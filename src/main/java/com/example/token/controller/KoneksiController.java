package com.example.token.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.token.Enumeration.ErrorEnum;
import com.example.token.model.Koneksi;
import com.example.token.payload.ErrorSchema;
import com.example.token.payload.GagalOutputSchema;
import com.example.token.payload.ResponseSchema;
import com.example.token.payload.koneksi.InputCreateKoneksi;
import com.example.token.payload.koneksi.InputDeleteKoneksi;
import com.example.token.payload.koneksi.InputGetKoneksiByStatus;
import com.example.token.payload.koneksi.InputUpdateKoneksi;
import com.example.token.payload.koneksi.ResponseCreateKoneksi;
import com.example.token.payload.koneksi.ResponseDataKoneksi;
import com.example.token.payload.koneksi.ResponseUpdateKoneksi;
import com.example.token.service.KoneksiService;

@RestController
public class KoneksiController {
	@Autowired
	KoneksiService koneksiService;
	
	//FIND KONEKSI BY STATUS
	@GetMapping("tokenkeybca/koneksi")
	public ResponseEntity<?> getKoneksiByStatus (@RequestBody InputGetKoneksiByStatus input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.GET_ALL);
		ResponseSchema<List<Koneksi>> responseSchema = new ResponseSchema<>(errorSchema);
		List<Koneksi> result = new ArrayList<>();
		
		try {
			result = koneksiService.findKoneksiByStatus(input);
		} catch (Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_GET_ALL);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//Delete Koneksi By UserID
	@DeleteMapping("tokenkeybca/koneksi")
	public ResponseEntity<?> deleteKoneksiByUserId (@RequestBody InputDeleteKoneksi input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
		ResponseSchema<ResponseDataKoneksi> responseSchema = new ResponseSchema<>(errorSchema);
		ResponseDataKoneksi result = new ResponseDataKoneksi();
		
		try {
			result = koneksiService.deleteKoneksi(input);
			//KONDISI USERID TIDAK ADA DALAM DB
			if(result.getId().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
				ResponseSchema<ResponseDataKoneksi> responseFail = new ResponseSchema<>(errorFail);
				result.setDateKoneksi(null);
				result.setId("");
				result.setJenisKoneksi("");
				result.setSerialNumber("");
				result.setStatus("");
				result.setUserId("");
				responseFail.setOutputSchema(result);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
		} catch (Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//CREATE KONEKSI
	@PostMapping("tokenkeybca/koneksi")
	public ResponseEntity<?> createKoneksi (@RequestBody InputCreateKoneksi input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.CREATE);
		ResponseSchema<ResponseCreateKoneksi> responseSchema = new ResponseSchema<>(errorSchema);
		ResponseCreateKoneksi result = new ResponseCreateKoneksi();
		
		try {
			result = koneksiService.createKoneksi(input);
			
			//KONDISI TIDAK MEMENUHI SYARAT
			if(result.getId().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Gagal Menyimpan Data, Input Param Tidak Memenuhi Syarat"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
		} catch (Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//UPDATE KONEKSI
	@PutMapping("tokenkeybca/koneksi")
	public ResponseEntity<?> updateKoneksi (@RequestBody InputUpdateKoneksi input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.UPDATE);
		ResponseSchema<Map<String, String>> responseSchema = new ResponseSchema<>(errorSchema);
		ResponseUpdateKoneksi result = new ResponseUpdateKoneksi();
		Map<String, String> map = new LinkedHashMap<>();
		try {
			result = koneksiService.updateKoneksi(input);
			//KONDISI TIDAK ADA UPDATE DATA UNTUK SEMUA FIELD
			if(result.getId().equalsIgnoreCase("No Update")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Tidak Ada Perubahan Data"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			//KONDISI TIDAK BISA UPDATE -> NILAI INPUT PARAM TIDAK SESUAI SYARAT
			if(result.getId().equals("") ) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Value Input Update Param Tidak Memenuhi SYarat"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}	
			
			map.put("id", result.getId());
			
			if(result.getSerialNumberNew() != null && !result.getSerialNumberNew().equals("")) {
				map.put("serial_number_old", result.getSerialNumberOld());
				map.put("serial_number_new", result.getSerialNumberNew());
			}
			
			if(result.getStatusNew() != null && !result.getStatusNew().equals("")) {
				map.put("status_old", result.getStatusOld());
				map.put("status_new", result.getStatusNew());
			}
		
			map.put("user_id", result.getUserId());
			
			if(result.getDateKoneksiNew() != null ) {
				map.put("date_koneksi_old", result.getDateKoneksiOld().toString());
				map.put("date_koneksi_new", result.getDateKoneksiNew().toString());
			}
			
			if(result.getJenisKoneksiNew() != null) {
				map.put("jenis_koneksi_old", result.getJenisKoneksiOld());
				map.put("jenis_koneksi_new", result.getJenisKoneksiNew());
			}
			if(result.getCabangNew() != null ) {
				map.put("cabang_old", result.getCabangOld());
				map.put("cabang_new", result.getCabangNew());
			}
		
			
		} catch (Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.UPDATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
				
		responseSchema.setOutputSchema(map);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
}
