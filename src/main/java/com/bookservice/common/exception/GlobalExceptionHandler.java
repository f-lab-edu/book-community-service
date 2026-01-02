package com.bookservice.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bookservice.common.exception.ErrorCode.ALREADY_EXIST_EMAIL;
import static com.bookservice.common.exception.ErrorCode.ALREADY_EXIST_NICKNAME;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConflict(DataIntegrityViolationException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        String code = "DUPLICATE_DATA";
        String error = "중복된 데이터가 존재합니다.";
        String message = e.getMostSpecificCause().getMessage();

        if (message.contains("uk_email")) {
            code = ALREADY_EXIST_EMAIL.getCode();
            error = ALREADY_EXIST_EMAIL.getError();
        }
        if (message.contains("uk_nick_name")) {
            code = ALREADY_EXIST_NICKNAME.getCode();
            error = ALREADY_EXIST_NICKNAME.getError();
        }
        ErrorMessage errorMessage = new ErrorMessage(code, error);

        logger.error(error + " : " + error);

        return ResponseEntity.status(status).body(errorMessage);
    }
}
