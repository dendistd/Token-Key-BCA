package com.example.token.payload.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUpdateTokenStatus {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("serial_number")
	private String serialNumber;
	
	@JsonProperty("cabang")
	private String cabang;
	
	@JsonProperty("status_new")
	private String statusNew;
	
	@JsonProperty("status_old")
	private String statusOld;

	public ResponseUpdateTokenStatus() {
		super();
	}

	public ResponseUpdateTokenStatus(String id, String serialNumber, String cabang, String statusOld) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.cabang = cabang;
		this.statusOld = statusOld;
	}

	public ResponseUpdateTokenStatus(String id, String serialNumber, String cabang, String statusNew,
			String statusOld) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.cabang = cabang;
		this.statusNew = statusNew;
		this.statusOld = statusOld;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCabang() {
		return cabang;
	}

	public void setCabang(String cabang) {
		this.cabang = cabang;
	}

	public String getStatusNew() {
		return statusNew;
	}

	public void setStatusNew(String statusNew) {
		this.statusNew = statusNew;
	}

	public String getStatusOld() {
		return statusOld;
	}

	public void setStatusOld(String statusOld) {
		this.statusOld = statusOld;
	}

	@Override
	public String toString() {
		return "ResponseUpdateTokenStatus [id=" + id + ", serialNumber=" + serialNumber + ", cabang=" + cabang
				+ ", statusNew=" + statusNew + ", statusOld=" + statusOld + "]";
	}
	
}
