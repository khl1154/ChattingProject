package com.clone.chat.util.exception;

public class ErrorTrace {
	
	public static String getName() {
		Exception ex = new Exception();
		return ex.getStackTrace()[1].getClassName() + "::" + ex.getStackTrace()[1].getMethodName();
	}
}
