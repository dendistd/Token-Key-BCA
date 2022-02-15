package com.example.token.payload.cabang;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputDeleteCabang {
	@JsonProperty("kode_cabang")
	private String kodeCabang;

	public InputDeleteCabang() {
		super();
	}

	public InputDeleteCabang(String kodeCabang) {
		super();
		this.kodeCabang = kodeCabang;
	}

	public String getKodeCabang() {
		return kodeCabang;
	}

	public void setKodeCabang(String kodeCabang) {
		this.kodeCabang = kodeCabang;
	}

	@Override
	public String toString() {
		return "InputDeleteCabang [kodeCabang=" + kodeCabang + "]";
	}
	
	
}
