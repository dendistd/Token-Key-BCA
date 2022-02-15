package com.example.token.service.imple;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.token.model.TokenModel;
import com.example.token.payload.token.InputCreateToken;
import com.example.token.payload.token.InputDeleteToken;
import com.example.token.payload.token.InputFindStatus;
import com.example.token.payload.token.InputUpdateToken;
import com.example.token.payload.token.InputUpdateTokenStatus;
import com.example.token.payload.token.ResponseCreateToken;
import com.example.token.payload.token.ResponseDataToken;
import com.example.token.payload.token.ResponseUpdateToken;
import com.example.token.payload.token.ResponseUpdateTokenStatus;
import com.example.token.repository.TokenRepo;
import com.example.token.service.TokenService;

@Service
public class TokenServiceImple implements TokenService {
	@Autowired
	TokenRepo tokenRepo;
	
	//FUNGSI UNTUK NUMBER LOG
	public String logNumber() {
		StringBuilder builder = new StringBuilder(6);
		Random random = new Random();	
		for(int i = 0; i < 6; i++) {
			builder.append(random.nextInt(10));
		}
		return builder.toString();
	}
			
	//FUNGSI DATE UNTUK LOG
	public String dateLog() {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}
		
	//GET ALL
	public List<TokenModel> getAllToken(){
		String valueLog = logNumber();
		List<TokenModel> result = tokenRepo.findAll();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menampilkan Semua Data Token ");
		return result;
	
	}
	//CREATE 
	public ResponseCreateToken createToken (InputCreateToken input) {
		String valueLog = logNumber();
		String stringId = RandomStringUtils.randomAlphabetic(5).toUpperCase();
		/* String id, String serialNumber, String cabang, String status */
		TokenModel objToken = new TokenModel(stringId,input.getSerialNumber().toUpperCase(), input.getStatus().toUpperCase());
		ResponseCreateToken result = new ResponseCreateToken(); 
		/* 	KONDISI : 
		 * 	a.	cabang bernilai NULL
			b.	status bernilai BARU
			c.	pastikan serialNumber tidak sama dengan data di database
			d.	Jika semua kondisi terpenuhi maka lakukan simpan data. Jika ada kondisi tidak terpenuhi maka batal simpan data.
			e.	Kirim error code dan message sesuai dengan kondisi */
	
//		if(tokenRepo.existsBySerialNumber(input.getSerialNumber().toUpperCase()) == false && input.getStatus().equalsIgnoreCase("baru")) {
		if(tokenRepo.existsBySerialNumber(input.getSerialNumber().toUpperCase()) == false ) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN BELUM ADA Dalam DB");
			if(input.getStatus().equalsIgnoreCase("baru")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Semua Kondisi Terpenuhi");
				objToken.setCabang(null);
				objToken = tokenRepo.save(objToken);
			}
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status Tidak Memenuhi Syarat");
				objToken.setStatus("");
			}

			
		}
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN Sudah ADA Dalam DB");
			objToken.setSerialNumber("");			
		}
		
		result.setId(objToken.getId());
		result.setCabang(objToken.getCabang());
		result.setSerialNumber(objToken.getSerialNumber());
		result.setStatus(objToken.getStatus());
		return result;
	}
	
	//DELETE BY SERIALNUMBER -? UPDATE STATUS MENJADI "HAPUS"
	public ResponseDataToken deleteTokenBySerialNumber (InputDeleteToken input) {
		String valueLog = logNumber();
		ResponseDataToken responFail = new ResponseDataToken();
		
		if(!tokenRepo.existsBySerialNumber(input.getSerialNumber().toUpperCase())) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data TIDAK ADA DALAM DB");
			responFail.setSerialNumber("");
			return responFail;
		}
		
		TokenModel findData = tokenRepo.findBySerialNumber(input.getSerialNumber().toUpperCase());
		ResponseDataToken objToken = new ResponseDataToken(findData.getId(), findData.getSerialNumber(), findData.getCabang(), findData.getStatus());
		/* String id, String serialNumber, String cabang, String status */
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Ditemukan");
		//JIKA VALUE FIELD STATUS SUDAH BERNILAI "HAPUS" -> RETURNKAN STRING ""
		if(!findData.getStatus().equalsIgnoreCase("hapus")) {
//			tokenRepo.deleteBySerialNumber(serialNumber);
			findData.setStatus("HAPUS");
			findData = tokenRepo.save(findData);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Sudah Terhapus");
		}
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Menghapus Data Yang Sudah Terhapus");
			objToken.setStatus("");
			return objToken;
		}
		objToken.setStatus(findData.getStatus());
		return objToken;
	}
	
	//UPDATE Cabang
	public ResponseUpdateToken updateTokenCabang (InputUpdateToken input) {
		String valueLog = logNumber();
		ResponseUpdateToken responGagal = new ResponseUpdateToken();
		//JIKA DATA TIDAK DITEMUKAN
		if(tokenRepo.existsBySerialNumber(input.getSerialNumber()) == false) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN TIDAK ADA Dalam DB");
			responGagal.setSerialNumber("");
			return responGagal;
		}
		TokenModel objToken = tokenRepo.findBySerialNumber(input.getSerialNumber());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data");
		/* String id, String serialNumberOld, String cabangOld, String statusOld */
		ResponseUpdateToken response = new ResponseUpdateToken(objToken.getId(), objToken.getSerialNumber(), objToken.getCabang(), objToken.getStatus());
		
		//Bisa update -> CABANG DB BEDA DENGAN INPUT CABANG 
		if(!(objToken.getCabang().equalsIgnoreCase(input.getCabang()) ) ) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Cabang DB BEDA Dengan Input");
			if(!input.getCabang().equals("") && !input.getCabang().startsWith(" ")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Cabang BUKAN String Kosong atau Berisi Spasi");
				objToken.setCabang(input.getCabang().toUpperCase());
				response.setCabangNew(objToken.getCabang().toUpperCase());

			}
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Cabang BERISI String Kosong atau Berisi Spasi");
				response.setCabangNew("");
				return response;
			}
			
		}
		//JIKA TIDAK ADA UPDATE 
		else {
			response.setCabangNew("No Update");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Update For Cabang");

		}
		
		// Bisa Update -> Status DB BEDA dengan Status  Input
		if(!objToken.getStatus().equalsIgnoreCase(input.getStatus())) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Status DB BEDA Dengan Status Input");
			if(input.getStatus().equalsIgnoreCase("baru") || input.getStatus().equalsIgnoreCase("aktif") || input.getStatus().equalsIgnoreCase("tdk aktif") || input.getStatus().equalsIgnoreCase("tutup")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status Sesuai Kondisi");
				objToken.setStatus(input.getStatus().toUpperCase());
				response.setStatusNew(objToken.getStatus().toUpperCase());
			}
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status TIDAK Sesuai Kondisi");
				response.setStatusNew("");
				return response;
			}
		}
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Update For Status");
			response.setStatusNew("No Update");
		}
		
		//	SAVE KE DB 
		objToken = tokenRepo.save(objToken);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Di Update = " + objToken.toString());

		return response;
	}

	//UPDATE TOKEN STATUS
	public ResponseUpdateTokenStatus updateTokenStatus (InputUpdateTokenStatus input) {
		String valueLog = logNumber();
		ResponseUpdateTokenStatus responGagal = new ResponseUpdateTokenStatus();
		//JIKA DATA TIDAK DITEMUKAN
		if(tokenRepo.existsBySerialNumber(input.getSerialNumber()) == false) {
			responGagal.setSerialNumber("");
			return responGagal;
		}
		TokenModel objToken = tokenRepo.findBySerialNumber(input.getSerialNumber());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data");
		/* String id, String serialNumberOld, String cabangOld, String statusOld */
		ResponseUpdateTokenStatus response = new ResponseUpdateTokenStatus(objToken.getId(), objToken.getSerialNumber(), objToken.getCabang(), objToken.getStatus());
		
		//BISA UPDATE-> status DB dan input status BEDA 
		if(!(objToken.getStatus().equalsIgnoreCase(input.getStatus()) ) ) {
			//BISA UPDATE STATUS SESUAI KONDISI SOAL-> BARU, AKTIF, TDK AKTIF,TUTUP
			if(input.getStatus().equalsIgnoreCase("baru") || input.getStatus().equalsIgnoreCase("aktif") || input.getStatus().equalsIgnoreCase("tdk aktif") || input.getStatus().equalsIgnoreCase("tutup")) {
				objToken.setStatus(input.getStatus().toUpperCase());
				objToken = tokenRepo.save(objToken);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Di Update = " + objToken.toString());
				
			}
			
			//TIDAK BISA UPDATE STATUS KARNA VALUE NYA TIDAK SESUAI KONDISI
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Update Data");
				response.setStatusNew("");
				return response;
			}
		}
		//JIKA TIDAK ADA UPDATE 
		else {
			response.setStatusNew("No Update");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Ada Update Data");
			return response;
		}
	
		response.setStatusNew(input.getStatus().toUpperCase());
		return response;
	}
	
	//FIND DATA TOKEN BY STATUS
	public List<ResponseDataToken> findTokenByStatus(InputFindStatus input) {
		String valueLog = logNumber();
		List<TokenModel> list = new ArrayList<>();
		list = tokenRepo.findByStatus(input.getStatus().toUpperCase());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data");
		List<ResponseDataToken> listResult = new ArrayList<>();
		
		//convert 
		for(TokenModel tokenModel : list) {
			ResponseDataToken dataToken = new ResponseDataToken(tokenModel.getId(), tokenModel.getSerialNumber(), tokenModel.getCabang(), tokenModel.getStatus());
			listResult.add(dataToken);
		}
		return listResult;
	}
	

}
