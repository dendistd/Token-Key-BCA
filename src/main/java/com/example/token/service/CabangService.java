package com.example.token.service;

import java.util.List;

import com.example.token.model.Cabang;
import com.example.token.payload.cabang.InputCreateCabang;
import com.example.token.payload.cabang.InputDeleteCabang;
import com.example.token.payload.cabang.InputUpdateCabang;
import com.example.token.payload.cabang.InputUpdateStatusCabang;
import com.example.token.payload.cabang.OutputGetCabang;
import com.example.token.payload.cabang.ResponseCreateCabang;
import com.example.token.payload.cabang.ResponseDataCabang;
import com.example.token.payload.cabang.ResponseUpdateNamaCabang;
import com.example.token.payload.cabang.ResponseUpdateCabang;

public interface CabangService {
	//CREATE
	public ResponseCreateCabang createCabang (InputCreateCabang input);
	
	//DELETE
	public ResponseDataCabang deleteCabang (InputDeleteCabang input);
	
	//UPDATE STATUS CABANG
	public ResponseUpdateCabang updateCabang (InputUpdateCabang input);
	
	//GET CABANG BY STATUS 
	public List<OutputGetCabang> getCabangByStatus (String input);

}
