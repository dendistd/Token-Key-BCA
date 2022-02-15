package com.example.token.payload.koneksi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputDeleteKoneksi {
	@JsonProperty("user_id")
	private String userID;

	public InputDeleteKoneksi() {
		super();
	}

	public InputDeleteKoneksi(String userID) {
		super();
		this.userID = userID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "InputDeleteKoneksi [userID=" + userID + "]";
	}
	
	
}
