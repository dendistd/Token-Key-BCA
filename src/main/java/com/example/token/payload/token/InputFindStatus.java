package com.example.token.payload.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputFindStatus {
	@JsonProperty("status")
	private String status;

	public InputFindStatus() {
		super();
	}

	public InputFindStatus(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InputFindStatus [status=" + status + "]";
	}
	

}
