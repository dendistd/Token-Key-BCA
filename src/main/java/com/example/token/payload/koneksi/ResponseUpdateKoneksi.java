package com.example.token.payload.koneksi;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUpdateKoneksi {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("date_old")
	private Date dateKoneksiOld;
	
	@JsonProperty("date_new")
	private Date dateKoneksiNew;
	
	@JsonProperty("serial_number_old")
	private String serialNumberOld;
	
	@JsonProperty("serial_number_new")
	private String serialNumberNew;
	
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("jenis_koneksi_old")
	private String jenisKoneksiOld;
	
	@JsonProperty("jenis_koneksi_new")
	private String jenisKoneksiNew;
	
	@JsonProperty("status_old")
	private String statusOld;
	
	@JsonProperty("status_new")
	private String statusNew;
	
	@JsonProperty("cabang_old")
	private String cabangOld;
	
	@JsonProperty("cabang_new")
	private String cabangNew;

	public ResponseUpdateKoneksi() {
		super();
	}
	
	/* String id, Date dateKoneksi, String serialNumber, String userId, String jenisKoneksi,
	String status */
	
	public String getId() {
		return id;
	}

	public ResponseUpdateKoneksi(String id, Date dateKoneksiOld, String serialNumberOld, String userId,
			String jenisKoneksiOld, String statusOld) {
		super();
		this.id = id;
		this.dateKoneksiOld = dateKoneksiOld;
		this.serialNumberOld = serialNumberOld;
		this.userId = userId;
		this.jenisKoneksiOld = jenisKoneksiOld;
		this.statusOld = statusOld;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateKoneksiOld() {
		return dateKoneksiOld;
	}

	public void setDateKoneksiOld(Date dateKoneksiOld) {
		this.dateKoneksiOld = dateKoneksiOld;
	}
	
	public Date getDateKoneksiNew() {
		return dateKoneksiNew;
	}

	public void setDateKoneksiNew(Date dateKoneksiNew) {
		this.dateKoneksiNew = dateKoneksiNew;
	}

	public String getSerialNumberOld() {
		return serialNumberOld;
	}

	public void setSerialNumberOld(String serialNumberOld) {
		this.serialNumberOld = serialNumberOld;
	}

	public String getSerialNumberNew() {
		return serialNumberNew;
	}

	public void setSerialNumberNew(String serialNumberNew) {
		this.serialNumberNew = serialNumberNew;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJenisKoneksiOld() {
		return jenisKoneksiOld;
	}

	public void setJenisKoneksiOld(String jenisKoneksiOld) {
		this.jenisKoneksiOld = jenisKoneksiOld;
	}

	public String getJenisKoneksiNew() {
		return jenisKoneksiNew;
	}

	public void setJenisKoneksiNew(String jenisKoneksiNew) {
		this.jenisKoneksiNew = jenisKoneksiNew;
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

	@Override
	public String toString() {
		return "ResponseUpdateKoneksi [id=" + id + ", dateKoneksiOld=" + dateKoneksiOld + ", dateKoneksiNew="
				+ dateKoneksiNew + ", serialNumberOld=" + serialNumberOld + ", serialNumberNew=" + serialNumberNew
				+ ", userId=" + userId + ", jenisKoneksiOld=" + jenisKoneksiOld + ", jenisKoneksiNew=" + jenisKoneksiNew
				+ ", statusOld=" + statusOld + ", statusNew=" + statusNew + ", cabangOld=" + cabangOld + ", cabangNew="
				+ cabangNew + "]";
	}

	


}
