package com.example.token.payload.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUpdateToken {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("serial_number")
	private String serialNumber;
	
	@JsonProperty("cabang_old")
	private String cabangOld;
	
	@JsonProperty("cabang_new")
	private String cabangNew;
	
	@JsonProperty("status_old")
	private String statusOld;
	
	@JsonProperty("status_new")
	private String statusNew;

	public ResponseUpdateToken() {
		super();
	}

	public ResponseUpdateToken(String id, String serialNumber, String cabangOld, String statusOld) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.cabangOld = cabangOld;
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

	public String getCabangOld() {
		return cabangOld;
	}

	public void setCabangOld(String cabangOld) {
		this.cabangOld = cabangOld;
	}

	public String getCabangNew() {
		return cabangNew;
	}

	public void setCabangNew(String cabangNew) {
		this.cabangNew = cabangNew;
	}

	public String getStatusOld() {
		return statusOld;
	}

	public void setStatusOld(String statusOld) {
		this.statusOld = statusOld;
	}

	public String getStatusNew() {
		return statusNew;
	}

	public void setStatusNew(String statusNew) {
		this.statusNew = statusNew;
	}

	@Override
	public String toString() {
		return "ResponseUpdateCabang [id=" + id + ", serialNumber=" + serialNumber + ", cabangOld=" + cabangOld
				+ ", cabangNew=" + cabangNew + ", statusOld=" + statusOld + ", statusNew=" + statusNew + "]";
	}


}
