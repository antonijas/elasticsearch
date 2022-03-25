package com.example.elasticsearch.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PreDestroy;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetRequest.Item;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DocumentMigrator implements ApplicationRunner {

  private final String DOC_ID = "770899393414434816|BITGO|TBTC|DEPOSIT";
  private final String DOC_ID2 = "774930573352767488|BITGO|TBTC|DEPOSIT";

  @Value("${wallet_index}")
  private String wallet_index;

  @Value("${userStaging}")
  private String userStaging;

  @Value("${passStaging}")
  private String passStaging;

  @Value("${hostStaging}")
  private String hostStaging;

  @Value("${portStaging}")
  private int portStaging;

  @Value("indexNameStaging")
  private String indexNameStaging;

  Logger logger = LoggerFactory.getLogger(DocumentMigrator.class);
  private TransactionsByWalletRepo transactionsByWalletRepo;
  private WalletInfoCopyRepo walletInfoCopyRepo;
  private RestHighLevelClient client;

  @Autowired
  public DocumentMigrator(TransactionsByWalletRepo transactionsByWalletRepo,
      RestHighLevelClient client,
      WalletInfoCopyRepo walletInfoCopyRepo) {
    this.transactionsByWalletRepo = transactionsByWalletRepo;
    this.client = client;
    this.walletInfoCopyRepo = walletInfoCopyRepo;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    saveWalletInfo();
    Iterable<WalletInfo> docs = transactionsByWalletRepo.findAll();
    for (WalletInfo doc : docs) {
      logger.info("fetched wallet info. transactions {}", doc.getTransactions());

    }
    createIndex();
    copyDocumentToNewIndex(docs);
    // readFromStaging();
    String jsonString = "{" +
        "\"partnerId\":\"123\"" +
        "}";
    addNewFields(jsonString);
  }

  private void copyDocumentToNewIndex(Iterable<WalletInfo> docs) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    // TODO: bulk?
    for (WalletInfo doc : docs) {
      String val = objectMapper.writeValueAsString(doc);
      IndexRequest r = new IndexRequest(wallet_index);
      r.type("_doc");
      r.id(doc.getId());
      r.source(val, XContentType.JSON);
      client.index(r, RequestOptions.DEFAULT);
    }
    logger.info("docs moved to new index ");
    Iterable<WalletInfoCopy> w = walletInfoCopyRepo.findAllById(List.of(DOC_ID, DOC_ID2));
    for (WalletInfoCopy doc_copy : w) {
      logger.info("reading from new index with spring data repo... {}", doc_copy);
    }
  }

  private void addNewFields(String json) throws IOException {
    MultiGetRequest multiGetRequest = new MultiGetRequest();
    multiGetRequest.add(new Item(wallet_index, "_doc", DOC_ID));
    multiGetRequest.add(new Item(wallet_index, "_doc", DOC_ID2));
    client.mget(multiGetRequest, RequestOptions.DEFAULT);

    // get only one....
    //GetRequest req = new GetRequest(wallet_index,
    //   "_doc",
    //  DOC_ID);
    //GetResponse response = client.get(req, RequestOptions.DEFAULT);

    UpdateRequest r = new UpdateRequest(wallet_index, "_doc", DOC_ID);
    r.doc(json, XContentType.JSON);
    r.type("_doc");
    UpdateRequest r2 = new UpdateRequest(wallet_index, "_doc", DOC_ID2);
    r2.doc(json, XContentType.JSON);
    r2.type("_doc");

    BulkRequest bulkRequest = new BulkRequest();
    bulkRequest.add(r);
    bulkRequest.add(r2);



    BulkResponse updateResponse = client.bulk(
        bulkRequest, RequestOptions.DEFAULT);
    logger.info("added new fields... {}", updateResponse);
  }


  private void createIndex() throws IOException {
    CreateIndexRequest request = new CreateIndexRequest(wallet_index);
    request.settings(Settings.builder()
        .put("index.number_of_shards", 3)
        .put("index.number_of_replicas", 2)
    );
    CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
    logger.info("index created: {}", indexResponse.index());

  }

  private void readFromStaging() throws IOException {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY,
        new UsernamePasswordCredentials(userStaging, passStaging));

    RestClientBuilder b = RestClient.builder(new HttpHost(
            hostStaging, portStaging, "https"))
        .setHttpClientConfigCallback(new HttpClientConfigCallback() {
          @Override
          public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
            return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
          }
        });

    RestHighLevelClient client = new RestHighLevelClient(b);

    GetRequest req = new GetRequest(indexNameStaging,
        "_doc",
        DOC_ID);
    GetResponse response = client.get(req, RequestOptions.DEFAULT);
    logger.info("reading from staging.... {}", response.getSource());
  }

  @PreDestroy
  private void deleteIndex() throws IOException {
    // TODO: fails if client is not recreated here
    RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    DeleteIndexRequest deleteRequest = new DeleteIndexRequest(wallet_index);
    Boolean response = client.indices().delete(deleteRequest, RequestOptions.DEFAULT).isAcknowledged();
    logger.info("index deleted: {}", response);
  }

  private void saveWalletInfo() throws ParseException {
    Map<String, Object> transactionInfo1 = new HashMap<>();
    transactionInfo1.put("fees", "0.00000000");
    transactionInfo1.put("amount", "0.03198000");
    transactionInfo1.put("purchasePrice", "61533.58");
    transactionInfo1.put("type", "REDEEM");
    transactionInfo1.put("toAddress", "");
    transactionInfo1.put("internalId", "REDEEM|770900624442130432");
    transactionInfo1.put("redeemCode", "VYW2-62W4-U310");
    transactionInfo1.put("at", "2021-10-28T06:44:04.60693Z");
    transactionInfo1.put("redemptionTimestamp", "2021-10-28T06:44:02.975351Z");
    transactionInfo1.put("phoneNumber", "2069706916");
    transactionInfo1.put("creationTimestamp", "2021-10-28T06:43:51.854976Z");
    transactionInfo1.put("fromAddress", "");
    transactionInfo1.put("id", "REDEEM|770900624442130432");
    transactionInfo1.put("state", "CONFIRMED");
    transactionInfo1.put("quoteCurrencyAmount", "2050.00");

    Map<String, Object> transactionInfo2 = new HashMap<>();
    transactionInfo2.put("fees", "0.00000000");
    transactionInfo2.put("amount", "0.00040618");
    transactionInfo2.put("purchasePrice", "68495.14");
    transactionInfo2.put("type", "REDEEM");
    transactionInfo2.put("toAddress", "");
    transactionInfo2.put("internalId", "REDEEM|774963079862550528");
    transactionInfo2.put("redeemCode", "AN8R-G548-TW2U");
    transactionInfo2.put("at", "2021-11-08T11:46:41.660504Z");
    transactionInfo2.put("redemptionTimestamp", "2021-11-08T11:46:40.781795Z");
    transactionInfo2.put("phoneNumber", "2069706916");
    transactionInfo2.put("creationTimestamp", "2021-11-08T11:46:36.639565Z");
    transactionInfo2.put("fromAddress", "");
    transactionInfo2.put("id", "REDEEM|774963079862550528");
    transactionInfo2.put("state", "CONFIRMED");
    transactionInfo2.put("quoteCurrencyAmount", "29.00");

    WalletInfo walletInfo1 = new WalletInfo(
        DOC_ID,
        List.of(new TransactionForWallet(transactionInfo1), new TransactionForWallet(transactionInfo2)));

    WalletInfo walletInfo2 = new WalletInfo(
        DOC_ID2,
        List.of(new TransactionForWallet(transactionInfo1)));
    transactionsByWalletRepo.saveAll(List.of(walletInfo1, walletInfo2));


  }
}
