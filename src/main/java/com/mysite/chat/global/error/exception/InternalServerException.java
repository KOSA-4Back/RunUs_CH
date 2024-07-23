package com.mysite.chat.global.error.exception;

import com.mysite.chat.global.error.errorCode.ErrorCode;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : BadRequestException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
public class InternalServerException extends CustomBaseException {
    public InternalServerException(ErrorCode errorCode){
        super(errorCode.getMessage(), errorCode);
    }
    public InternalServerException(){
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
    public InternalServerException(String message){
        super(message, ErrorCode.INTERNAL_SERVER_ERROR);
    }
}