package com.mysite.chat.global.mongo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * packageName    : com.mysite.chat.global.mongo
 * fileName       : ObjectIdSerializer
 * author         : Yeong-Huns
 * date           : 2024-07-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        Yeong-Huns       최초 생성
 */
public class ObjectIdSerializer extends StdSerializer<ObjectId> {

    public ObjectIdSerializer() {
        this(null);
    }

    public ObjectIdSerializer(Class<ObjectId> t) {
        super(t);
    }

    @Override
    public void serialize(ObjectId objectId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(objectId.toHexString());
    }
}
