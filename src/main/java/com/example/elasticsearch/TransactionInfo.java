package com.example.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

//TODO: indexName, type?
@Document(indexName = "wallet")
public class TransactionInfo {

  @Id
  private String id;

  private Double fees;
  private Double amount;
  private Double purchasePrice;
  private String type;
  private String toAddress;

  public TransactionInfo(String id, Double fees, Double amount, Double purchasePrice, String type, String toAddress) {
    this.id = id;
    this.fees = fees;
    this.amount = amount;
    this.purchasePrice = purchasePrice;
    this.type = type;
    this.toAddress = toAddress;
  }

  public Double getFees() {
    return fees;
  }

  public void setFees(Double fees) {
    this.fees = fees;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(Double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getToAddress() {
    return toAddress;
  }

  public void setToAddress(String toAddress) {
    this.toAddress = toAddress;
  }
}
