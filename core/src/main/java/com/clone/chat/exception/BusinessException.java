package com.clone.chat.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3269944136608201287L;
	ErrorCodes code;
	String function;
	
	public BusinessException(ErrorCodes code, String function) {
		this.code = code;
		this.function = function;
	}
}
