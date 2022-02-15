package com.example.token.payload.cabang;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUpdateCabang {
	@JsonProperty("kode_cabang")
	private String kodeCabang;
	
	@JsonProperty("nama_cabang_old")
	private String namaCabangOld;
	
	@JsonProperty("nama_cabang_new")
	private String namaCabangNew;
	
	@JsonProperty("status_new")
	private String statusNew;
	
	@JsonProperty("status_old")
	private String statusOld;

	public ResponseUpdateCabang() {
		super();
	}
	
	public ResponseUpdateCabang(String kodeCabang, String namaCabangOld, String statusOld) {
		super();
		this.kodeCabang = kodeCabang;
		this.namaCabangOld = namaCabangOld;
		this.statusOld = statusOld;
	}

	public ResponseUpdateCabang(String kodeCabang, String namaCabangOld, String namaCabangNew, String statusNew,
			String statusOld) {
		super();
		this.kodeCabang = kodeCabang;
		this.namaCabangOld = namaCabangOld;
		this.namaCabangNew = namaCabangNew;
		this.statusNew = statusNew;
		this.statusOld = statusOld;
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
		return "ResponseUpdateStatusCabang [kodeCabang=" + kodeCabang + ", namaCabangOld=" + namaCabangOld
				+ ", namaCabangNew=" + namaCabangNew + ", statusNew=" + statusNew + ", statusOld=" + statusOld + "]";
	}

	
	

}
