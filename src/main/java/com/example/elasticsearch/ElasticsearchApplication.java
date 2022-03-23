package com.example.elasticsearch;

import java.text.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticsearchApplication {

  public static void main(String[] args) throws ParseException {

    SpringApplication.run(ElasticsearchApplication.class, args);
  }
}