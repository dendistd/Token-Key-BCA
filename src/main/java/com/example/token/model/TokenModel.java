package com.example.token.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dendi_token")
public class TokenModel {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "cabang")
	private String cabang;
	
	@Column(name = "status")
	private String status;

	public TokenModel() {
		super();
	}
	
	public TokenModel(String id ,String serialNumber, String status) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.status = status;
	}

	public TokenModel(String id, String serialNumber, String cabang, String status) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.cabang = cabang;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", serialNumber=" + serialNumber + ", cabang=" + cabang + ", status=" + status + "]";
	}
	
	
}
