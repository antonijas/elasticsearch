package com.example.elasticsearch.domain;

import java.sql.Timestamp;
import java.util.Map;
import org.joda.time.DateTime;

public class TransactionForWallet {

  private Double fees;
  private Double amount;
  private Double purchasePrice;
  private String type;
  private String toAddress;
  private String internalId;
  private String redeemCode;
  private Timestamp at;
  private Timestamp redemptionTimestamp;
  private Long phoneNumber;
  private DateTime creationTimestamp;
  private String fromAddress;
  private String id;
  private String state;
  private Double quoteCurrencyAmount;

  public TransactionForWallet(Map<String, Object> transactionInfo) {

    this.fees = Double.valueOf((String) transactionInfo.get("fees"));
    this.amount = Double.valueOf((String) transactionInfo.get("amount"));
    this.purchasePrice = Double.valueOf((String) transactionInfo.get("amount"));
    this.type = (String) transactionInfo.get("type");
    this.toAddress = (String) transactionInfo.get("toAddress");
    this.internalId = (String) transactionInfo.get("internalId");
    this.redeemCode = (String) transactionInfo.get("redeemCode");
    // TODO: take care of timestamp precision, uncomment those parts below

    //  this.at = (Timestamp) transactionInfo.get("at");
    // this.redemptionTimestamp = (Timestamp) transactionInfo.get("redemptionTimestamp");
    this.phoneNumber = Long.valueOf((String) transactionInfo.get("phoneNumber"));
    // this.creationTimestamp = (DateTime) transactionInfo.get("creationTimestamp");
    this.fromAddress = (String) transactionInfo.get("fromAddress");
    this.id = (String) transactionInfo.get("id");
    this.state = (String) transactionInfo.get("state");
    this.quoteCurrencyAmount = Double.valueOf((String) transactionInfo.get("quoteCurrencyAmount"));
  }

  protected TransactionForWallet() {
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

  public String getInternalId() {
    return internalId;
  }

  public void setInternalId(String internalId) {
    this.internalId = internalId;
  }

  public String getRedeemCode() {
    return redeemCode;
  }

  public void setRedeemCode(String redeemCode) {
    this.redeemCode = redeemCode;
  }

  public Timestamp getAt() {
    return at;
  }

  public void setAt(Timestamp at) {
    this.at = at;
  }

  public Timestamp getRedemptionTimestamp() {
    return redemptionTimestamp;
  }

  public void setRedemptionTimestamp(Timestamp redemptionTimestamp) {
    this.redemptionTimestamp = redemptionTimestamp;
  }

  public Long getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public DateTime getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(DateTime creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public String getFromAddress() {
    return fromAddress;
  }

  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Double getQuoteCurrencyAmount() {
    return quoteCurrencyAmount;
  }

  public void setQuoteCurrencyAmount(Double quoteCurrencyAmount) {
    this.quoteCurrencyAmount = quoteCurrencyAmount;
  }


}
