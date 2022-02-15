package com.example.token.payload.koneksi;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseCreateKoneksi {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("date")
	private Date dateKoneksi;
	
	@JsonProperty("serial_number")
	private String serialNumber;
	
	@JsonProperty("jenis_koneksi")
	private String jenisKoneksi;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("cabang")
	private String cabang;

	public ResponseCreateKoneksi() {
		super();
	}
	
	public ResponseCreateKoneksi(String serialNumber, String status, String cabang) {
		super();
		this.serialNumber = serialNumber;
		this.status = status;
		this.cabang = cabang;
	}

	public ResponseCreateKoneksi(String id, Date dateKoneksi, String serialNumber, String jenisKoneksi, String status,
			String cabang) {
		super();
		this.id = id;
		this.dateKoneksi = dateKoneksi;
		this.serialNumber = serialNumber;
		this.jenisKoneksi = jenisKoneksi;
		this.status = status;
		this.cabang = cabang;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateKoneksi() {
		return dateKoneksi;
	}

	public void setDateKoneksi(Date dateKoneksi) {
		this.dateKoneksi = dateKoneksi;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getJenisKoneksi() {
		return jenisKoneksi;
	}

	public void setJenisKoneksi(String jenisKoneksi) {
		this.jenisKoneksi = jenisKoneksi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCabang() {
		return cabang;
	}

	public void setCabang(String cabang) {
		this.cabang = cabang;
	}

	@Override
	public String toString() {
		return "ResponseCreateKoneksi [id=" + id + ", dateKoneksi=" + dateKoneksi + ", serialNumber=" + serialNumber
				+ ", jenisKoneksi=" + jenisKoneksi + ", status=" + status + ", cabang=" + cabang + "]";
	}
	

}
