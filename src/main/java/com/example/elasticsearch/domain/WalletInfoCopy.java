package com.example.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "wallet_copy", type = "_doc", createIndex = false)
public class WalletInfoCopy extends WalletInfo{

}
