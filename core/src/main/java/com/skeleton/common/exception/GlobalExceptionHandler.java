package com.skeleton.common.exception;

import com.skeleton.config.error.ErrorCode;
import com.skeleton.config.error.ErrorResponse;
import com.skeleton.config.error.exception.DefaultRuntimeException;
import com.skeleton.config.error.exception.unchecked.ExceptionWithData;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DefaultRuntimeException.class)
    protected ResponseEntity<?> handleDefaultRuntimeException(DefaultRuntimeException e) {

        log.error("Exception", e);
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode(), e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler(ExceptionWithData.class)
    protected ResponseEntity<?> handleExceptionWithData(ExceptionWithData e) {

        log.error("Exception", e);
        return new ResponseEntity<>(ErrorResponse.withData(e.getErrorCode(), e.getMessage(), e.getData()),
                e.getHttpStatus());
    }

    // IllegalArgumentException 에러 처리
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {

        log.error("Exception", e);

        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INVALID_PARAMETER, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // @Valid 어노테이션으로 넘어오는 에러 처리
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ErrorResponse.ValidationError> validationErrorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        log.error("Exception", ex);

        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INVALID_PARAMETER, validationErrorList), status);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<?> handleExpiredException(ExpiredJwtException e) {

        log.error("Exception", e);

        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.EXPIRED_TOKEN, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {

        log.error("Exception", e);

        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
