package com.example.token.payload.koneksi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputDeleteKoneksi {
	@JsonProperty("user_id")
	private String userId;

	public InputDeleteKoneksi() {
		super();
	}

	public InputDeleteKoneksi(String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "InputDeleteKoneksi [userId=" + userId + "]";
	}
	
	
}
