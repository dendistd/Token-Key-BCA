package com.example.token.payload.cabang;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputGetCabangByStatus {

	private String status;

	public InputGetCabangByStatus() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
