package com.example.token.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.token.Enumeration.ErrorEnum;
import com.example.token.payload.ErrorSchema;
import com.example.token.payload.ResponseSchema;
import com.example.token.payload.koneksi.InputCreateKoneksi;
import com.example.token.payload.koneksi.ResponseCreateKoneksi;
import com.example.token.service.HistoryKoneksiService;

@RestController
public class HistoryKoneksiController {
	@Autowired
	HistoryKoneksiService historyKoneksiService;
	
	@PostMapping("tokenkeybca/historykoneksi")
	public void createHistoryKoneksi (@RequestBody InputCreateKoneksi input){
		historyKoneksiService.createHistoryKoneksi(input);
	}

}
