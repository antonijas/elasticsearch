package com.example.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TransactionsByWalletRepo extends ElasticsearchRepository<TransactionInfo, Long> {

}
