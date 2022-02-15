package com.example.token.payload;

import java.util.HashMap;
import java.util.Map;

import com.example.token.Enumeration.ErrorEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorSchema {
	@JsonProperty("error_code")
	private String errorCode;
	
	@JsonProperty("error_message")
	private Map<String, String> errorMessage;

	
	public ErrorSchema() {
		super();
	}
	public ErrorSchema(String errorCode, Map<String, String> errorMessage) {
		
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	//Buat custom constructor yang terima inputnya ErrorEnum 
	public ErrorSchema(ErrorEnum errorEnum) {
		setErrorEnum(errorEnum);
		}
	//buat setter custom setErrorEnum
	public void setErrorEnum(ErrorEnum errorEnum) {
		this.errorCode = errorEnum.getErrorCode();
		Map<String, String> map = new HashMap<String, String>();
		map.put("indonesian", errorEnum.getErrorMessageIndonesian());
		map.put("english", errorEnum.getErrorMessageEnglish());
		
		this.errorMessage = map;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Map<String, String> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(Map<String, String> errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public String toString() {
		return "ErrorSchema [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

}
