package ru.codesquad.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.time.DateTimeException;

@RestControllerAdvice
@Slf4j
public class StatsErrorHandler {
    @ExceptionHandler({ValidationException.class,
            DateTimeException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestHandler(final Exception e) {
        log.info("400: {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerErrorHandler(final Throwable e) {
        log.info("500: {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    private class ErrorResponse {
        String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}