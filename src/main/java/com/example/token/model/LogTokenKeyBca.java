package com.example.token.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dendi_token_key_bca_log")
public class LogTokenKeyBca {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "tindakan")
	private String tindakan;
	
	@Column(name = "keterangan")
	private String keterangan;
	
	@Column(name = "tabel")
	private String tabel;
	
	@Column(name = "id_data")
	private String idData;

	public LogTokenKeyBca() {
		super();
	}

	public LogTokenKeyBca(String id, Date createDate, String tindakan, String keterangan, String tabel, String idData) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.tindakan = tindakan;
		this.keterangan = keterangan;
		this.tabel = tabel;
		this.idData = idData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTindakan() {
		return tindakan;
	}

	public void setTindakan(String tindakan) {
		this.tindakan = tindakan;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public String getTabel() {
		return tabel;
	}

	public void setTabel(String tabel) {
		this.tabel = tabel;
	}

	public String getIdData() {
		return idData;
	}

	public void setIdData(String idData) {
		this.idData = idData;
	}

	@Override
	public String toString() {
		return "LogTokenKeyBca [id=" + id + ", createDate=" + createDate + ", tindakan=" + tindakan + ", keterangan="
				+ keterangan + ", tabel=" + tabel + ", idData=" + idData + "]";
	}
	
}
