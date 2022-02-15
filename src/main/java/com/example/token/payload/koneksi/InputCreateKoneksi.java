package com.example.token.payload.koneksi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputCreateKoneksi {

	@JsonProperty("serial_number")
	private String serialNumber;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("kode_cabang")
	private String kodeCabang;

	public InputCreateKoneksi() {
		super();
	}

	public InputCreateKoneksi(String serialNumber, String status, String userId, String kodeCabang) {
		super();
		this.serialNumber = serialNumber;
		this.status = status;
		this.userId = userId;
		this.kodeCabang = kodeCabang;
	}
	

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKodeCabang() {
		return kodeCabang;
	}

	public void setKodeCabang(String kodeCabang) {
		this.kodeCabang = kodeCabang;
	}

	@Override
	public String toString() {
		return "InputCreateKoneksi [serialNumber=" + serialNumber + ", status=" + status + ", userId=" + userId
				+ ", kodeCabang=" + kodeCabang + "]";
	}
	
	
}
