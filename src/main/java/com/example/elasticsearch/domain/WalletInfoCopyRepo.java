package com.example.elasticsearch.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WalletInfoCopyRepo extends ElasticsearchRepository<WalletInfoCopy, String> {
}
