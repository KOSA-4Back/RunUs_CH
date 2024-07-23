package com.mysite.chat.global.mongo;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * packageName    : com.mysite.chat.global.mongo
 * fileName       : MappingMongoConverter
 * author         : Yeong-Huns
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        Yeong-Huns       최초 생성
 */
@Component
public class CustomMappingMongoConverter extends MappingMongoConverter {

    public CustomMappingMongoConverter(MongoDatabaseFactory mongoDbFactory, MongoMappingContext mappingContext) {
        super(new DefaultDbRefResolver(mongoDbFactory), mappingContext);
        setCustomConversions(new MongoCustomConversions(Collections.emptyList()));
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
