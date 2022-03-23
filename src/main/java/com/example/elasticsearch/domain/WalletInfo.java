package com.example.elasticsearch.domain;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "transactions_by_wallet", type = "_doc")
public class WalletInfo {

  @Id
  private String id;

  @Field(type = FieldType.Nested)
  private List<TransactionForWallet> transactions;

  protected WalletInfo() {
  }

  public WalletInfo(String id, List<TransactionForWallet> transactions) {
    this.id = id;
    this.transactions = transactions;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<TransactionForWallet> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<TransactionForWallet> transactions) {
    this.transactions = transactions;
  }

}
