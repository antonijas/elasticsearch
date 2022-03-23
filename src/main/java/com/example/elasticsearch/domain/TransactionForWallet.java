package com.example.elasticsearch.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TransactionForWallet {


  private String fees;
  private String amount;
  private String purchasePrice;
  private String type;
  private String toAddress;
  private String internalId;
  private String redeemCode;
  private Date at;
  private Date redemptionTimestamp;
  private Long phoneNumber;
  private Date creationTimestamp;
  private String fromAddress;
  private String id;
  private String state;
  private String quoteCurrencyAmount;

  public TransactionForWallet(Map<String, Object> transactionInfo) throws ParseException {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS");

    this.fees = (String) transactionInfo.get("fees");
    this.amount = (String) transactionInfo.get("amount");
    this.purchasePrice = (String) transactionInfo.get("amount");
    this.type = (String) transactionInfo.get("type");
    this.toAddress = (String) transactionInfo.get("toAddress");
    this.internalId = (String) transactionInfo.get("internalId");
    this.redeemCode = (String) transactionInfo.get("redeemCode");

    this.at = sdf.parse(String.valueOf(transactionInfo.get("at")));
    this.redemptionTimestamp = sdf.parse(String.valueOf(transactionInfo.get("redemptionTimestamp")));
    this.phoneNumber = Long.valueOf((String) transactionInfo.get("phoneNumber"));
    this.creationTimestamp = sdf.parse(String.valueOf(transactionInfo.get("creationTimestamp")));
    this.fromAddress = (String) transactionInfo.get("fromAddress");
    this.id = (String) transactionInfo.get("id");
    this.state = (String) transactionInfo.get("state");
    this.quoteCurrencyAmount = (String) transactionInfo.get("quoteCurrencyAmount");
  }

  protected TransactionForWallet() {
  }


  public String getFees() {
    return fees;
  }

  public void setFees(String fees) {
    this.fees = fees;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(String purchasePrice) {
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


  public Long getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
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

  public String getQuoteCurrencyAmount() {
    return quoteCurrencyAmount;
  }

  public void setQuoteCurrencyAmount(String quoteCurrencyAmount) {
    this.quoteCurrencyAmount = quoteCurrencyAmount;
  }

  public Date getAt() {
    return at;
  }

  public void setAt(Date at) {
    this.at = at;
  }

  public Date getRedemptionTimestamp() {
    return redemptionTimestamp;
  }

  public void setRedemptionTimestamp(Date redemptionTimestamp) {
    this.redemptionTimestamp = redemptionTimestamp;
  }

  public Date getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }
}
