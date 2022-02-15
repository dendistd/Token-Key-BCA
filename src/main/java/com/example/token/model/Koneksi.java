package com.example.token.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dendi_koneksi")
public class Koneksi {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "date_koneksi")
	private Date dateKoneksi;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "jenis_koneksi")
	private String jenisKoneksi;
	
	@Column(name = "status")
	private String status;

	public Koneksi() {
		super();
	}

	public Koneksi(String id, Date dateKoneksi, String serialNumber, String userId, String jenisKoneksi,
			String status) {
		super();
		this.id = id;
		this.dateKoneksi = dateKoneksi;
		this.serialNumber = serialNumber;
		this.userId = userId;
		this.jenisKoneksi = jenisKoneksi;
		this.status = status;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		return "Koneksi [id=" + id + ", dateKoneksi=" + dateKoneksi + ", serialNumber=" + serialNumber + ", userId="
				+ userId + ", jenisKoneksi=" + jenisKoneksi + ", status=" + status + "]";
	}
	
	
}
