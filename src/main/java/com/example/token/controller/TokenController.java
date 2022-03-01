package com.example.token.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils.Null;
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
import com.example.token.model.TokenModel;
import com.example.token.payload.ErrorSchema;
import com.example.token.payload.GagalOutputSchema;
import com.example.token.payload.ResponseSchema;
import com.example.token.payload.token.InputCreateToken;
import com.example.token.payload.token.InputDeleteToken;
import com.example.token.payload.token.InputUpdateToken;
import com.example.token.payload.token.InputUpdateTokenStatus;
import com.example.token.payload.token.ResponseCreateToken;
import com.example.token.payload.token.ResponseDataToken;
import com.example.token.payload.token.ResponseUpdateToken;
import com.example.token.payload.token.ResponseUpdateTokenStatus;
import com.example.token.service.TokenService;

@RestController
public class TokenController {
	@Autowired
	TokenService tokenService;
	
	//CREATE 
	@PostMapping("tokenkeybca/token")
	public ResponseEntity<?> createToken (@RequestBody InputCreateToken input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.CREATE);
		ResponseCreateToken result = new ResponseCreateToken();
		ResponseSchema<ResponseCreateToken> responseSchema = new ResponseSchema<>(errorSchema);
		try {
			result = tokenService.createToken(input);
			if(result.getSerialNumber().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("SN Is Already Exist On DB"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			if(result.getStatus().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Status Value Is Incorrect !"));
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
	
	//DELETE -> UPDATE STATUS MENJADI "HAPUS"
	@DeleteMapping("tokenkeybca/token")
	public ResponseEntity<?> deleteTokenBySerialNumber (@RequestBody InputDeleteToken input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
		ResponseDataToken result = new ResponseDataToken();
		ResponseSchema<ResponseDataToken> responseSchema = new ResponseSchema<>(errorSchema);
		
		try {
			result = tokenService.deleteTokenBySerialNumber(input);
			//JIKA SN TIDAK ADA DALAM DB
			if(result.getSerialNumber().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("SN Tidak Ada Dalam DB"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);

			}
			//TIDAK BISA DELETE DATA YG STATUS AKTIF
			if(result.getStatus().equals("Can't Delete")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Can't Delete Data Because Status = 'AKTIF'"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			//JIKA DATA SUDAH TERHAPUS RETURNKAN STRING ""
			if(result.getStatus().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
				ResponseSchema<ResponseDataToken> responseFail = new ResponseSchema<>(errorFail);
				result.setCabang("");
				result.setId("");
				result.setSerialNumber("");
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
	
	//UPDATE TOKEN
	@PutMapping("tokenkeybca/token")
	public ResponseEntity<?> updateCabang(@RequestBody InputUpdateToken input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.UPDATE);
		ResponseSchema<Map<String, String>> responseSchema = new ResponseSchema<>(errorSchema);
		ResponseUpdateToken result = new ResponseUpdateToken();
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			result = tokenService.updateTokenCabang(input);
			//JIKA DATA TIDAK DITEMUKAN
			if(result.getSerialNumber().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<Object> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema("null");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			//TIDAK BISA UPDATE -> STATUS CABANG = 'HAPUS'
			if(result.getCabangNew().equals("Can't Update")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Can't Update Cabang, Because Status Cabang is 'HAPUS'"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);

			}
			//KONDISI VALUE CABANG BERISI STRING KOSONG "" ATAU SPASI
			if(result.getCabangNew().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Value Cabang For Updating Data Is Incorrect !"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			//KONDISI STATUS INCORRECT
			if(result.getStatusNew().equals("")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Value Status For Updating Data Is Incorrect !"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			//TIDAK ADA UPDATE 
			if(result.getCabangNew().equals("No Update") && result.getStatusNew().equals("No Update")) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Tidak Ada Perubahan Data !"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			map.put("id", result.getId());
			map.put("serial_number", result.getSerialNumber());
			
			if(!result.getCabangNew().equalsIgnoreCase("No Update")) {
				map.put("cabang_old", result.getCabangOld());
				map.put("cabang_new", result.getCabangNew().toUpperCase());
			}
			//Kondisi Cabang TIDAK ADA UPDATE
			if(result.getCabangNew().equalsIgnoreCase("No Update")) {
				map.put("cabang", result.getCabangOld());
			}
			
			if(!result.getStatusNew().equalsIgnoreCase("No Update")) {
				map.put("status_old", result.getStatusOld());
				map.put("status_new", result.getStatusNew().toUpperCase());
			}
			//Kondisi Status TIDAK ADA UPDATE
			if(result.getStatusNew().equalsIgnoreCase("No Update")) {
				map.put("status", result.getStatusOld());
			}
			
		} catch(Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		responseSchema.setOutputSchema(map);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
			
	//GET TOKEN BY STATUS 
	@GetMapping("tokenkeybca/token/{input}")
	public ResponseEntity<?> findByStatus(@PathVariable("input") String input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.FIND_ONE);
		ResponseSchema<List<ResponseDataToken>> responseSchema = new ResponseSchema<>(errorSchema);
		List<ResponseDataToken> result = new ArrayList<>();
		
		try {
			result = tokenService.findTokenByStatus(input);
			
		} catch (Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_FIND_ONE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
}
