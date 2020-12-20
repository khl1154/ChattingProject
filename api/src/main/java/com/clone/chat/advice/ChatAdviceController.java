package com.clone.chat.advice;

import com.clone.chat.code.MsgCode;
import com.clone.chat.model.error.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ChatAdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> noSuchElementException(Throwable ex, WebRequest webRequest) {
        logingAndSetMsg(ex, webRequest);
        Error e = new Error();
        e.setCode(MsgCode.ERROR_NOSUCHELEMENTEXCEPTION.name());
        e.setMessage(Optional.ofNullable(ex.getMessage()).orElseGet(ex::toString));
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> throwable(Throwable ex, WebRequest webRequest) {
        logingAndSetMsg(ex, webRequest);
        Error e = new Error();
        e.setCode(MsgCode.ERROR_UNKNOWN.name());
        e.setMessage(Optional.ofNullable(ex.getMessage()).orElseGet(ex::toString));
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }


    private void logingAndSetMsg(Throwable ex, WebRequest webRequest) {
        ServletWebRequest sw = (ServletWebRequest) webRequest;
        HttpServletRequest request = sw.getRequest();
        String param = request.getParameterMap().entrySet().stream().map(it -> it.getKey() + "=" + String.join(", ", Arrays.asList(it.getValue()))).collect(Collectors.joining("&"));
        log.error(ex.getMessage() + "|" + request.getRequestURI() + "|" + param, ex);
    }
}
