package com.mysite.chat.global.mongo;

import com.mysite.chat.global.error.errorCode.ResponseCode;
import com.mysite.chat.global.error.exception.NotFoundException;
import org.bson.types.ObjectId;

/**
 * packageName    : com.mysite.chat.global.mongo
 * fileName       : ObjectIdUtils
 * author         : Yeong-Huns
 * date           : 2024-07-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-27        Yeong-Huns       최초 생성
 */
public record ObjectIdConverter(String id) { // String -> ObjectId
    public static ObjectId fromString(String id) {
        try{
        return new ObjectId(id);
        } catch (Exception e){
            throw new NotFoundException(ResponseCode.INVALID_OBJECT_ID);
        }
    }
}
