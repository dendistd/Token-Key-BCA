package com.example.token.payload.cabang;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseCreateCabang {
	@JsonProperty("kode_cabang")
	private String kodeCabang;
	
	@JsonProperty("serial_number")
	private String serialNumber;

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("cabang")
	private String namaCabang;

	public ResponseCreateCabang() {
		super();
	}

	public ResponseCreateCabang(String kodeCabang, String serialNumber, String status, String namaCabang) {
		super();
		this.kodeCabang = kodeCabang;
		this.serialNumber = serialNumber;
		this.status = status;
		this.namaCabang = namaCabang;
	}

	public String getKodeCabang() {
		return kodeCabang;
	}

	public void setKodeCabang(String kodeCabang) {
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

	public String getNamaCabang() {
		return namaCabang;
	}

	public void setNamaCabang(String namaCabang) {
		this.namaCabang = namaCabang;
	}

	@Override
	public String toString() {
		return "ResponseCreateCabang [kodeCabang=" + kodeCabang + ", serialNumber=" + serialNumber + ", status="
				+ status + ", namaCabang=" + namaCabang + "]";
	}
	
	
	


}
