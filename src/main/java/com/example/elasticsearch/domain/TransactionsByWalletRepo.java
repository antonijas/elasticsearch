package com.example.elasticsearch.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TransactionsByWalletRepo extends ElasticsearchRepository<WalletInfo, String> {

}
