package com.example.token.service;

import java.util.List;

import com.example.token.model.Koneksi;
import com.example.token.payload.koneksi.InputCreateKoneksi;
import com.example.token.payload.koneksi.InputDeleteKoneksi;
import com.example.token.payload.koneksi.InputGetKoneksiByStatus;
import com.example.token.payload.koneksi.InputUpdateKoneksi;
import com.example.token.payload.koneksi.ResponseCreateKoneksi;
import com.example.token.payload.koneksi.ResponseDataKoneksi;
import com.example.token.payload.koneksi.ResponseUpdateKoneksi;

public interface KoneksiService {
	//DELETE
	public ResponseDataKoneksi deleteKoneksi(InputDeleteKoneksi input);
	
	//CREATE KONEKSI
	public ResponseCreateKoneksi createKoneksi (InputCreateKoneksi input);
	
	//FIND KONEKSI BY STATUS
	public List<Koneksi> findKoneksiByStatus(InputGetKoneksiByStatus input);
	
	//UPDATE KONEKSI
	public ResponseUpdateKoneksi updateKoneksi (InputUpdateKoneksi input);

}
