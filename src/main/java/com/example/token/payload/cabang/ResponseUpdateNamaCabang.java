package com.example.token.payload.cabang;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUpdateNamaCabang {
	@JsonProperty("kode_cabang")
	private String kodeCabang;
	
	@JsonProperty("nama_cabang_old")
	private String namaCabangOld;
	
	@JsonProperty("nama_cabang_new")
	private String namaCabangNew;
	
	@JsonProperty("status")
	private String status;

	public ResponseUpdateNamaCabang() {
		super();
	}

	
	public ResponseUpdateNamaCabang(String kodeCabang, String namaCabangOld, String status) {
		super();
		this.kodeCabang = kodeCabang;
		this.namaCabangOld = namaCabangOld;
		this.status = status;
	}


	public ResponseUpdateNamaCabang(String kodeCabang, String namaCabangOld, String namaCabangNew, String status) {
		super();
		this.kodeCabang = kodeCabang;
		this.namaCabangOld = namaCabangOld;
		this.namaCabangNew = namaCabangNew;
		this.status = status;
	}

	public String getKodeCabang() {
		return kodeCabang;
	}

	public void setKodeCabang(String kodeCabang) {
		this.kodeCabang = kodeCabang;
	}

	public String getNamaCabangOld() {
		return namaCabangOld;
	}

	public void setNamaCabangOld(String namaCabangOld) {
		this.namaCabangOld = namaCabangOld;
	}

	public String getNamaCabangNew() {
		return namaCabangNew;
	}

	public void setNamaCabangNew(String namaCabangNew) {
		this.namaCabangNew = namaCabangNew;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseUpdateNamaCabang [kodeCabang=" + kodeCabang + ", namaCabangOld=" + namaCabangOld
				+ ", namaCabangNew=" + namaCabangNew + ", status=" + status + "]";
	}
	
	

}
