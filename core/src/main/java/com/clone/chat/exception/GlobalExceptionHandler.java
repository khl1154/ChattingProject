package com.clone.chat.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.clone.chat.model.ResponseForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ResponseForm> businessException(BusinessException exception) {
		ErrorCodes code = exception.getCode();
		String funcName = exception.getFunction();

		log.error(code.toString() + " : " + funcName);
		
		return ResponseEntity.ok(new ResponseForm(code, funcName));
	}
}
