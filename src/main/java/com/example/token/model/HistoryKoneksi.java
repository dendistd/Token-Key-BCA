package com.example.token.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dendi_history_koneksi")
public class HistoryKoneksi {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "status_awal")
	private String statusAwal;
	
	@Column(name = "status_akhir")
	private String statusAkhir;
	
	@Column(name = "keterangan")
	private String keterangan;

	public HistoryKoneksi() {
		super();
	}

	public HistoryKoneksi(String id, String serialNumber, String userId, String statusAwal, String statusAkhir,
			String keterangan) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.userId = userId;
		this.statusAwal = statusAwal;
		this.statusAkhir = statusAkhir;
		this.keterangan = keterangan;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatusAwal() {
		return statusAwal;
	}

	public void setStatusAwal(String statusAwal) {
		this.statusAwal = statusAwal;
	}

	public String getStatusAkhir() {
		return statusAkhir;
	}

	public void setStatusAkhir(String statusAkhir) {
		this.statusAkhir = statusAkhir;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	@Override
	public String toString() {
		return "HistoryKoneksi [id=" + id + ", serialNumber=" + serialNumber + ", userId=" + userId + ", statusAwal="
				+ statusAwal + ", statusAkhir=" + statusAkhir + ", keterangan=" + keterangan + "]";
	}
	
	

}
