package com.mysite.chat;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Collections;

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
@EnableMongoAuditing
@Configuration
public class MongoDBConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext
    ) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new CustomMappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));  // _class 필드를 제거하는 설정
        return converter;
    }

    public static class CustomMappingMongoConverter extends MappingMongoConverter {

        public CustomMappingMongoConverter(DbRefResolver dbRefResolver, MongoMappingContext mappingContext) {
            super(dbRefResolver, mappingContext);
            setCustomConversions(new MongoCustomConversions(Collections.emptyList()));
            setTypeMapper(defaultMongoTypeMapper());
        }

        private MongoTypeMapper defaultMongoTypeMapper() {
            return new DefaultMongoTypeMapper(null);  // This disables the _class field
        }

        @Override
        public void write(Object source, Bson target) {
            super.write(source, target);
            if (target instanceof Document) {
                ((Document) target).putIfAbsent("profile_url", null);
                ((Document) target).putIfAbsent("deleted_at", null);
            }
        }
    }
}