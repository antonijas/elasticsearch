package com.example.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

//TODO: indexName, type?
@Document(indexName = "wallet")
public class TransactionInfo {

  @Id
  private Long id;

  private Double fees;
  private Double amount;
  private Double purchasePrice;
  private String type;
  private String toAddress;

}
