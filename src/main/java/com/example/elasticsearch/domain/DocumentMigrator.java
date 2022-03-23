package com.example.elasticsearch.domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PreDestroy;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DocumentMigrator implements ApplicationRunner {

  @Value("${wallet_index}")
  private String wallet_index;

  Logger logger = LoggerFactory.getLogger(DocumentMigrator.class);
  private TransactionsByWalletRepo transactionsByWalletRepo;
  private RestHighLevelClient client;

  @Autowired
  public DocumentMigrator(TransactionsByWalletRepo transactionsByWalletRepo,
      RestHighLevelClient client) {
    this.transactionsByWalletRepo = transactionsByWalletRepo;
    this.client = client;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    saveWalletInfo();
    Optional<WalletInfo> doc = transactionsByWalletRepo.findById("770899393414434816|BITGO|TBTC|DEPOSIT");
    if (doc.isPresent()) {
      logger.info("fetched wallet info. transactions {}", doc.get().getTransactions());
    }
    createIndex();
    copyDocumentToNewIndex(doc.get());
  }

  public void copyDocumentToNewIndex(WalletInfo doc) {
    // pročitat s prvog indexa, namapirat, preselit na drugi index
    // TODO: implement....

  }

  public void createIndex() throws IOException {
    CreateIndexRequest request = new CreateIndexRequest(wallet_index);
    request.settings(Settings.builder()
        .put("index.number_of_shards", 3)
        .put("index.number_of_replicas", 2)
    );

    CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
    logger.info("index created: {}", indexResponse.index());

  }

  @PreDestroy
  private void deleteIndex() throws IOException {
    // TODO: this fails if I don't recreate client here....
    RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    DeleteIndexRequest deleteRequest = new DeleteIndexRequest(wallet_index);
    Boolean response = client.indices().delete(deleteRequest, RequestOptions.DEFAULT).isAcknowledged();
    logger.info("index deleted: {}", response);

  }

  private void saveWalletInfo() {
    Map<String, Object> walletInfo1 = new HashMap<>();
    walletInfo1.put("fees", "0.00000000");
    walletInfo1.put("amount", "0.03198000");
    walletInfo1.put("purchasePrice", "61533.58");
    walletInfo1.put("type", "REDEEM");
    walletInfo1.put("toAddress", "");
    walletInfo1.put("internalId", "REDEEM|770900624442130432");
    walletInfo1.put("redeemCode", "VYW2-62W4-U310");
    walletInfo1.put("at", "2021-10-28T06:44:04.60693Z");
    walletInfo1.put("redemptionTimestamp", "2021-10-28T06:44:02.975351Z");
    walletInfo1.put("phoneNumber", "2069706916");
    walletInfo1.put("creationTimestamp", "2021-10-28T06:43:51.854976Z");
    walletInfo1.put("fromAddress", "");
    walletInfo1.put("id", "REDEEM|770900624442130432");
    walletInfo1.put("state", "CONFIRMED");
    walletInfo1.put("quoteCurrencyAmount", "2050.00");

    Map<String, Object> walletInfo2 = new HashMap<>();
    walletInfo2.put("fees", "0.00000000");
    walletInfo2.put("amount", "0.00040618");
    walletInfo2.put("purchasePrice", "68495.14");
    walletInfo2.put("type", "REDEEM");
    walletInfo2.put("toAddress", "");
    walletInfo2.put("internalId", "REDEEM|774963079862550528");
    walletInfo2.put("redeemCode", "AN8R-G548-TW2U");
    walletInfo2.put("at", "2021-11-08T11:46:41.660504Z");
    walletInfo2.put("redemptionTimestamp", "2021-11-08T11:46:40.781795Z");
    walletInfo2.put("phoneNumber", "2069706916");
    walletInfo2.put("creationTimestamp", "2021-11-08T11:46:36.639565Z");
    walletInfo2.put("fromAddress", "");
    walletInfo2.put("id", "REDEEM|774963079862550528");
    walletInfo2.put("state", "CONFIRMED");
    walletInfo2.put("quoteCurrencyAmount", "29.00");

    WalletInfo walletInfo = new WalletInfo(
        "770899393414434816|BITGO|TBTC|DEPOSIT",
        List.of(new TransactionForWallet(walletInfo1), new TransactionForWallet(walletInfo2)));
    transactionsByWalletRepo.save(walletInfo);
  }
}
