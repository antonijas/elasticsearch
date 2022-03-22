package com.example.elasticsearch;

import com.example.elasticsearch.domain.TransactionForWallet;
import com.example.elasticsearch.domain.TransactionsByWalletRepo;
import com.example.elasticsearch.domain.WalletInfo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@SpringBootApplication
public class ElasticsearchApplication implements ApplicationRunner {

  @Autowired
  public ElasticsearchApplication(TransactionsByWalletRepo transactionsByWalletRepo) {
    this.transactionsByWalletRepo = transactionsByWalletRepo;
  }

  public static void main(String[] args) {
    SpringApplication.run(ElasticsearchApplication.class, args);
  }

  Logger logger = LoggerFactory.getLogger(ElasticsearchApplication.class);
  private TransactionsByWalletRepo transactionsByWalletRepo;
  private static final String WALLET_INDEX = "walletinfo_copy";

  public String createIndex() throws IOException {

    //ClientConfiguration clientConfiguration =
     //   ClientConfiguration.builder().connectedTo("localhost:9200").build();
    //RestHighLevelClient client = RestClients.create(clientConfiguration).rest();
    //IndexRequest request = new IndexRequest(WALLET_INDEX);
    //client.index(request, RequestOptions.DEFAULT);
    return "a";

  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    System.out.println(new Date());
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS");
    DateTime dt = new DateTime("2021-10-28T06:44:04.60693Z");
    System.out.println(dt);
    System.out.println(sdf.parse("2021-10-28T06:44:04.60693Z"));

    //saveWalletInfo();
    //Iterable<WalletInfo> t = transactionsByWalletRepo.findAll();
    //t.forEach(i -> logger.info("----{}", i.getTransactions()));
   // createIndex();

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