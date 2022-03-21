package com.example.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticsearchApplication  implements ApplicationRunner {

  @Autowired
  public ElasticsearchApplication(TransactionsByWalletRepo transactionsByWalletRepo) {
    this.transactionsByWalletRepo = transactionsByWalletRepo;
  }

  private TransactionsByWalletRepo transactionsByWalletRepo;

  public static void main(String[] args) {
    SpringApplication.run(ElasticsearchApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    TransactionInfo t= new TransactionInfo("33535803", 0.0001,
       0.123, 100.0, "REDEEM", "Heinzelova 33");
    transactionsByWalletRepo.save(t);
  }
}
