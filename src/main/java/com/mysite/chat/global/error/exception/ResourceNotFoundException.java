package com.mysite.chat.global.error.exception;

import com.mysite.chat.global.error.errorCode.ErrorCode;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : ResourceNotFoundException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
public class ResourceNotFoundException extends NotFoundException {
    public ResourceNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
