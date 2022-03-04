package com.example.token.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.token.Enumeration.ErrorEnum;
import com.example.token.model.Cabang;
import com.example.token.payload.ErrorSchema;
import com.example.token.payload.GagalOutputSchema;
import com.example.token.payload.ResponseSchema;
import com.example.token.payload.cabang.InputCreateCabang;
import com.example.token.payload.cabang.InputDeleteCabang;
import com.example.token.payload.cabang.InputUpdateCabang;
import com.example.token.payload.cabang.InputUpdateStatusCabang;
import com.example.token.payload.cabang.OutputGetCabang;
import com.example.token.payload.cabang.ResponseCreateCabang;
import com.example.token.payload.cabang.ResponseDataCabang;
import com.example.token.payload.cabang.ResponseUpdateNamaCabang;
import com.example.token.payload.cabang.ResponseUpdateCabang;
import com.example.token.service.CabangService;

@RestController
public class CabangController {
	@Autowired
	CabangService cabangService;
	
	//GET CABANG BY STATUS
	@GetMapping("tokenkeybca/cabang/{input}")
	public ResponseEntity<?> getCabangByStatus (@PathVariable("input") String input) {
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.GET_ALL);
		ResponseSchema<List<OutputGetCabang>> responseSchema = new ResponseSchema<>(errorSchema);
		List<OutputGetCabang> result = new ArrayList<>();
		try {
			result = cabangService.getCabangByStatus(input);
		} catch (Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_GET_ALL);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//CREATE CABANG
	@PostMapping("tokenkeybca/cabang")
	public ResponseEntity<?> createCabang (@RequestBody InputCreateCabang input) {
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.CREATE);
		ResponseSchema<ResponseCreateCabang> responseSchema = new ResponseSchema<>(errorSchema);
		ResponseCreateCabang result = new ResponseCreateCabang();
		
		try {
			result = cabangService.createCabang(input);
			//KONDISI KODE_CABANG SUDAH ADA DALAM DB
			if(result.getKodeCabang().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Kode Cabang Sudah Ada Dalam DB"));
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseFail);
			}
			//KONDISI GAGAL CREATE
			if( result.getStatus().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Value Input Parameter Tidak Memenuhi Kondisi"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
		} catch (Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
			return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(responseFail);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//DELETE 
	@DeleteMapping("tokenkeybca/cabang")
	public ResponseEntity<?> deleteCabang (@RequestBody InputDeleteCabang input) {
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
		ResponseSchema<ResponseDataCabang> responseSchema = new ResponseSchema<>(errorSchema);
		ResponseDataCabang result = new ResponseDataCabang();
		
		try {
			result = cabangService.deleteCabang(input);
			if(result.getKodeCabang().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Kode Cabang Tidak Ada Dalam DB"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			if(result.getStatus().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
				ResponseSchema<ResponseDataCabang> responseFail = new ResponseSchema<>(errorFail);
				result.setKodeCabang("");
				result.setNamaCabang("");
				result.setStatus("");
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
	
	//UPDATE CABANG
	@PutMapping("tokenkeybca/cabang")
	public ResponseEntity<?> updateCabang (@RequestBody InputUpdateCabang input) {
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.UPDATE);
		ResponseSchema<Map<String, String>> responseSchema = new ResponseSchema<>(errorSchema);
		ResponseUpdateCabang result = new ResponseUpdateCabang();
		Map<String, String> map = new LinkedHashMap<>();
		
//		try {
			result = cabangService.updateCabang(input);
			//KONDISI KODE_CABANG TIDAK ADA DALAM DB
			if(result.getKodeCabang().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<String> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema("null");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			//KONDISI NAMA CABANG BERNILAI STRING KOSONG
			if(result.getNamaCabangNew().equals("Can't Update")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Tidak Bisa Mengupdate Nama Cabang Dengan Value String Kosong!"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			//KONDISI VALUE STATUS TIDAK MEMENUHI SYARAT
			if(result.getStatusNew().equalsIgnoreCase("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Value Status Tidak Memenuhi Syarat"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
					
			//KONDISI TIDAK ADA UPDATE
			if(result.getStatusNew().equalsIgnoreCase("No Update") && result.getNamaCabangNew().equalsIgnoreCase("No Update")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Tidak Ada Perubahan Data"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			map.put("kode_cabang", result.getKodeCabang());
			
			if(!result.getNamaCabangNew().equalsIgnoreCase("No Update")) {
				map.put("nama_cabang_old", result.getNamaCabangOld());
				map.put("nama_cabang_new", result.getNamaCabangNew().toUpperCase());
			}
			
			//Jika NAMA Cabang nya TIDAK ADA UPDATE 
			if(result.getNamaCabangNew().equalsIgnoreCase("No Update")) {
				map.put("nama_cabang", result.getNamaCabangOld());
			}
			
			if(!result.getStatusNew().equalsIgnoreCase("No Update")) {
				map.put("status_old", result.getStatusOld());
				map.put("status_new", result.getStatusNew().toUpperCase());
			}
			//Jika Status Cabang TIDAK ADA UPDATE 
			if(result.getStatusNew().equalsIgnoreCase("No Update")) {
				map.put("status", result.getStatusOld());
			}
			
			
//		} catch (Exception e) {
//			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
//			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
//			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
//		}
		responseSchema.setOutputSchema(map);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
}
