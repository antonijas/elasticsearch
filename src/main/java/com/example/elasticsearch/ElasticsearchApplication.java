package com.example.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElasticsearchApplication {
  @Value("${es_host}")
  private String es_host;

  @Value("${es_port}")
  private int es_port;


  @Bean
  RestHighLevelClient restHighLevelClient(){
    return new RestHighLevelClient(RestClient.builder(new HttpHost(es_host, es_port, "http")));
  }

  public static void main(String[] args) {
    SpringApplication.run(ElasticsearchApplication.class, args);
  }
}