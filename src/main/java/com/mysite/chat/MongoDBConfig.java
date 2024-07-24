package com.mysite.chat;

import com.mysite.chat.global.mongo.CustomMappingMongoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * packageName    : com.mysite.chat
 * fileName       : MongoDBConfig
 * author         : Yeong-Huns
 * date           : 2024-07-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-21        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@Configuration
public class MongoDBConfig { // 위치가 여기여야 _class 명이 저장안됨

    @Bean
    public CustomMappingMongoConverter customMappingMongoConverter(MongoDatabaseFactory mongoDatabaseFactory, MongoMappingContext mongoMappingContext) {
        return new CustomMappingMongoConverter(mongoDatabaseFactory, mongoMappingContext);
    }


    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory, CustomMappingMongoConverter customMappingMongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, customMappingMongoConverter);
    }
}