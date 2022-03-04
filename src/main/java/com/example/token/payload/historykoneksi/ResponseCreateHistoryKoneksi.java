package com.example.token.payload.historykoneksi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseCreateHistoryKoneksi {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("serial_number")
	private String serialNumber;
	
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("status_awal")
	private String statusAwal;
	
	@JsonProperty("status_akhir")
	private String statusAkhir;
	
	@JsonProperty("keterangan")
	private String keterangan;

	public ResponseCreateHistoryKoneksi() {
		super();
	}

	public ResponseCreateHistoryKoneksi(String id, String serialNumber, String userId, String statusAwal,
			String statusAkhir) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.userId = userId;
		this.statusAwal = statusAwal;
		this.statusAkhir = statusAkhir;
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
		return "ResponseCreateHistoryKoneksi [id=" + id + ", serialNumber=" + serialNumber + ", userId=" + userId
				+ ", statusAwal=" + statusAwal + ", statusAkhir=" + statusAkhir + ", keterangan=" + keterangan + "]";
	}
	
	

}
