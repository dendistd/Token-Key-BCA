package com.example.token.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dendi_cabang")
public class Cabang {
	@Id
	@Column(name = "kode_cabang")
	private String kodeCabang;
	
	@Column(name = "nama_cabang")
	private String namaCabang;
	
	@Column(name = "status")
	private String status;

	public Cabang() {
		super();
	}

	public Cabang(String kodeCabang, String namaCabang, String status) {
		super();
		this.kodeCabang = kodeCabang;
		this.namaCabang = namaCabang;
		this.status = status;
	}

	public String getKodeCabang() {
		return kodeCabang;
	}

	public void setKodeCabang(String kodeCabang) {
		this.kodeCabang = kodeCabang;
	}

	public String getNamaCabang() {
		return namaCabang;
	}

	public void setNamaCabang(String namaCabang) {
		this.namaCabang = namaCabang;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Cabang [kodeCabang=" + kodeCabang + ", namaCabang=" + namaCabang + ", status=" + status + "]";
	}
	
	
}
