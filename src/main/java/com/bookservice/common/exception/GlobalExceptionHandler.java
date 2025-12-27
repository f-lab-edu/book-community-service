package com.bookservice.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  예외가 발생할 시 해당 예외에 대한 HTTP 상태 코드와 메시지를 생성하여 ResponseEntity 객체로 반환합니다.
 *  반환된 객체는 클라이언트에게 전송되어 예외 처리 결과를 알려줍니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BookException.class)
    public ResponseEntity<?> handleBookServiceException(BookException e){
        HttpStatus status = e.getErrorCode().getHttpStatus();
        String code = e.getErrorCode().getCode();
        String error = e.getErrorCode().getError();
        ErrorMessage errorMessage = new ErrorMessage(code, error);

        logger.error(e.getErrorCode().getCode() + " : " + e.getErrorCode().getError());

        return ResponseEntity.status(status).body(errorMessage);
    }
}
