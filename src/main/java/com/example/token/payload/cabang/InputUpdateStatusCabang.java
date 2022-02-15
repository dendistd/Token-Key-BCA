package com.example.token.payload.cabang;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputUpdateStatusCabang {
	@JsonProperty("kode_cabang")
	private String kodeCabang;
	
	@JsonProperty("status")
	private String status;

	public InputUpdateStatusCabang(String kodeCabang, String status) {
		super();
		this.kodeCabang = kodeCabang;
		this.status = status;
	}

	public String getKodeCabang() {
		return kodeCabang;
	}

	public void setKodeCabang(String kodeCabang) {
		this.kodeCabang = kodeCabang;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InputUpdateStatusCabang [kodeCabang=" + kodeCabang + ", status=" + status + "]";
	}
	
	

}
