package com.clone.chat.util;

import java.util.LinkedHashMap;

import com.clone.chat.util.exception.ErrorCodes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ResponseForm extends LinkedHashMap<String, Object>{

	private static final long serialVersionUID = -4098281931429200073L;

	public ResponseForm() {
		put("result", "success");
	}
	
	public ResponseForm(String key, Object obj) {
		put(key, obj);
	}
	
	public ResponseForm(ErrorCodes code, String funcName) {
		put("errMsg", code);
		put("errClass", funcName);
	}
}
