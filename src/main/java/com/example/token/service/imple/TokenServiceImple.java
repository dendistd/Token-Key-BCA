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
import com.example.token.model.Cabang;
import com.example.token.model.LogTokenKeyBca;
import com.example.token.model.TokenModel;
import com.example.token.payload.token.InputCreateToken;
import com.example.token.payload.token.InputDeleteToken;
import com.example.token.payload.token.InputUpdateToken;
import com.example.token.payload.token.InputUpdateTokenStatus;
import com.example.token.payload.token.ResponseCreateToken;
import com.example.token.payload.token.ResponseDataToken;
import com.example.token.payload.token.ResponseUpdateToken;
import com.example.token.payload.token.ResponseUpdateTokenStatus;
import com.example.token.repository.CabangRepo;
import com.example.token.repository.LogTokenKeyBcaRepository;
import com.example.token.repository.TokenRepo;
import com.example.token.service.TokenService;

@Service
public class TokenServiceImple implements TokenService {
	@Autowired
	TokenRepo tokenRepo;
	@Autowired
	CabangRepo cabangRepo;
	@Autowired
	LogTokenKeyBcaRepository logTokenKeyBcaRepository;
	
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
	
	//GET TOKEN BY STATUS 
	public List<ResponseDataToken> findTokenByStatus(String input) {
		String valueLog = logNumber();
		List<TokenModel> list = new ArrayList<>();
		list = tokenRepo.findByStatus(input.toUpperCase());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data");
		List<ResponseDataToken> listResult = new ArrayList<>();

		LogTokenKeyBca log = new LogTokenKeyBca();	
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTabel("DENDI_TOKEN");
		log.setTindakan("SELECT");
		
		//convert 
		for(TokenModel tokenModel : list) {
			ResponseDataToken dataToken = new ResponseDataToken(tokenModel.getId(), tokenModel.getSerialNumber(), tokenModel.getCabang(), tokenModel.getStatus());
			listResult.add(dataToken);
		}
		//INSERT DATA DENDI_TOKEN_KEY_BCA_LOG , Dengan KETERANGAN 'BERHASIL'
		if(listResult.size() != 0) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL -> INSERT Data Ke DENDI_TOKEN_KEY_BCA_LOG");
			log.setKeterangan("BERHASIL SELECT DATA TOKEN BERDASARKAN STATUS = "+input.toUpperCase());
			
		}
		//INSERT DATA DENDI_TOKEN_KEY_BCA_LOG , Dengan KETERANGAN 'GAGAL'
		else{
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> INSERT Data Ke DENDI_TOKEN_KEY_BCA_LOG");
			log.setKeterangan("GAGAL SELECT DATA TOKEN BERDASARKAN STATUS = "+input.toUpperCase());
		}
		//SAVE KE DENDI_TOKEN_KEY_BCA_LOG
		log = logTokenKeyBcaRepository.save(log);
		
		return listResult;
	}
	
	//CREATE 
	public ResponseCreateToken createToken (InputCreateToken input) {
		String valueLog = logNumber();
		String stringId = RandomStringUtils.randomAlphabetic(5).toUpperCase();
		TokenModel objToken = new TokenModel(stringId,input.getSerialNumber().toUpperCase(), input.getStatus().toUpperCase());
		ResponseCreateToken result = new ResponseCreateToken(); 
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setIdData(objToken.getId());
		log.setTabel("DENDI_TOKEN");
		log.setTindakan("CREATE");
		
		if(tokenRepo.existsBySerialNumber(input.getSerialNumber().toUpperCase()) == false ) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN BELUM ADA Dalam DB");
			if(input.getStatus().equalsIgnoreCase("baru")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Semua Kondisi Terpenuhi");
				objToken.setCabang(null);
				objToken = tokenRepo.save(objToken);
				
				//KETERANGAN BERHASIL INSERT DATA DENDI_TOKEN_KEY_BCA_LOG		
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL -> INSERT DENDI_TOKEN_KEY_BCA_LOG");
				log.setKeterangan("BERHASIL CREATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+objToken.getSerialNumber());
			
			}
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status Tidak Memenuhi Syarat");
				objToken.setStatus("");
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> INSERT DENDI_TOKEN_KEY_BCA_LOG");
				log.setKeterangan("GAGAL CREATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+objToken.getSerialNumber());
			}			
		}
		
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN Sudah ADA Dalam DB");
			objToken.setSerialNumber("");			
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> INSERT DENDI_TOKEN_KEY_BCA_LOG");
			log.setKeterangan("GAGAL CREATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+input.getSerialNumber().toUpperCase());
		}
		//SAVE KE TABEL DENDI_TOKEN_KEY_BCA_LOG
		log = logTokenKeyBcaRepository.save(log);
		
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
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		//INSERT KE TABEL DENDI_TOKEN_KEY_BCA_LOG
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTabel("DENDI_TOKEN");
		log.setTindakan("DELETE");
				
		if(!tokenRepo.existsBySerialNumber(input.getSerialNumber().toUpperCase())) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data TIDAK ADA DALAM DB");
			responFail.setSerialNumber("");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL ->Insert Data Tabel Dendi_Token_Key_Bca_Log");	
			log.setKeterangan("GAGAL DELETE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+input.getSerialNumber());
			log = logTokenKeyBcaRepository.save(log);
			
			return responFail;
		}
		TokenModel findData = tokenRepo.findBySerialNumber(input.getSerialNumber().toUpperCase());
		ResponseDataToken objToken = new ResponseDataToken(findData.getId(), findData.getSerialNumber(), findData.getCabang(), findData.getStatus());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Ditemukan");
		
		log.setIdData(findData.getId());
		
		//TIDAK BISA DELETE -> STATUS "AKTIF"
		if(findData.getStatus().equals("AKTIF")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Delete Data -> Status 'AKTIF'");
			objToken.setStatus("Can't Delete");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL ->Insert Data Tabel Dendi_Token_Key_Bca_Log");	
			log.setKeterangan("GAGAL DELETE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+findData.getSerialNumber());
			log = logTokenKeyBcaRepository.save(log);
			return objToken;
		}
		//TIDAK BISA DELETE DATA YANG SUDAH DIHAPUS
		if(findData.getStatus().equalsIgnoreCase("hapus")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Menghapus Data Yang Sudah Terhapus");
			objToken.setStatus("");
			
			//SAVE KE DENDI_TOKEN_KEY_BCA_LOG
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL ->Insert Data Tabel Dendi_Token_Key_Bca_Log");	
			log.setKeterangan("GAGAL DELETE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+findData.getSerialNumber());
			log = logTokenKeyBcaRepository.save(log);
			return objToken;
		}
		//JIKA VALUE FIELD STATUS SUDAH BERNILAI "HAPUS" -> RETURNKAN STRING ""
		if(!findData.getStatus().equalsIgnoreCase("hapus")) {
			findData.setStatus("HAPUS");
			findData = tokenRepo.save(findData);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Sudah Terhapus");
			
			//KETERAGAN BERHASIL INSERT DATA TABEL DENDI_TOKEN_KEY_BCA_LOG
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL ->Insert Data Tabel Dendi_Token_Key_Bca_Log");	
			log.setKeterangan("BERHASIL DELETE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+findData.getSerialNumber());
			//SAVE KE DENDI_TOKEN_KEY_BCA_LOG
			log = logTokenKeyBcaRepository.save(log);
		}

		
		objToken.setStatus(findData.getStatus());
		return objToken;
	}
	
	//UPDATE TOKEN
	public ResponseUpdateToken updateTokenCabang (InputUpdateToken input) {
		String valueLog = logNumber();
		ResponseUpdateToken responGagal = new ResponseUpdateToken();
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTabel("DENDI_TOKEN");
		log.setTindakan("UPDATE");
		
		//JIKA DATA TIDAK DITEMUKAN
		if(tokenRepo.existsBySerialNumber(input.getSerialNumber().toUpperCase()) == false) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN TIDAK ADA Dalam DB");
			responGagal.setSerialNumber("");
			//SAVE KE TABEL DENDI_TOKEN_KEY_BCA_LOG
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL Insert Data Tabel Dendi_Token_Key_Bca_Log");			
			log.setKeterangan("GAGAL UPDATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+input.getSerialNumber());
			log = logTokenKeyBcaRepository.save(log);
			
			return responGagal;
		}
		TokenModel objToken = tokenRepo.findBySerialNumber(input.getSerialNumber());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data");
		/* String id, String serialNumberOld, String cabangOld, String statusOld */
		ResponseUpdateToken response = new ResponseUpdateToken(objToken.getId(), objToken.getSerialNumber(), objToken.getCabang(), objToken.getStatus());
		Cabang dataCabang = cabangRepo.findById(input.getCabang().toUpperCase()).get();
		
		//INSERT KE TABEL DENDI-TOKEN_KEY_BCA
		log.setIdData(objToken.getId());
		
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
						
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL Insert Data Tabel Dendi_Token_Key_Bca_Log");			
				log.setKeterangan("GAGAL UPDATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+objToken.getSerialNumber());
				log = logTokenKeyBcaRepository.save(log);
						
				return response;
			}
		}
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Update For Status");
			response.setStatusNew("No Update");
		}
		
		//Bisa update -> CABANG DB BEDA DENGAN INPUT CABANG 
		if(objToken.getCabang() == null || !objToken.getCabang().startsWith(input.getCabang().toUpperCase()) ) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kondisi Bisa Update");
			if(!input.getCabang().equals("") && !input.getCabang().startsWith(" ")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Cabang BUKAN String Kosong atau Berisi Spasi");
				//BISA UPDATE -> STATUS CABANG BUKAN HAPUS
				if(!dataCabang.getStatus().equals("HAPUS")) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Bisa Update ->Status Cabang BUKAN 'HAPUS'");
					objToken.setCabang(dataCabang.getKodeCabang() + " - " + dataCabang.getNamaCabang());
					response.setCabangNew(objToken.getCabang().toUpperCase());
				}
				else {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - CANT UPDATE -> Status Cabang = 'HAPUS'");
					response.setCabangNew("Can't Update");
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL Insert Data Tabel Dendi_Token_Key_Bca_Log");			
					log.setKeterangan("GAGAL UPDATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+objToken.getSerialNumber());
					log = logTokenKeyBcaRepository.save(log);
					return response;
				}
			}
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Cabang BERISI String Kosong atau Berisi Spasi");
				response.setCabangNew("");
				
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL Insert Data Tabel Dendi_Token_Key_Bca_Log");			
				log.setKeterangan("GAGAL UPDATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+objToken.getSerialNumber());
				log = logTokenKeyBcaRepository.save(log);
				return response;
			}	
		}
		//JIKA TIDAK ADA UPDATE 
		else {
			 response.setCabangNew("No Update");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Update For Cabang");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL Insert Data Tabel Dendi_Token_Key_Bca_Log");			
			log.setKeterangan("GAGAL UPDATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+objToken.getSerialNumber());
		}
		
		
		
		//BERHASIL UPDATE DATA TOKEN
		if(!response.getCabangNew().equals("No Update") || !response.getStatusNew().equals("No Update")) {
//			SAVE KE DB 
			objToken = tokenRepo.save(objToken);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Di Update = " + objToken.toString());
			//KETERANGAN BERHASIL INSERT KE TABEL DENDI_TOKEN_KEY_BCA_LOG
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL Insert Data Tabel Dendi_Token_Key_Bca_Log");			
			log.setKeterangan("BERHASIL UPDATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+objToken.getSerialNumber());
			
		}	
		//TIDAK ADA UPDATE 
		else {
			//KETERANGAN GAGAL INSERT KE TABEL DENDI_TOKEN_KEY_BCA_LOG
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL Insert Data Tabel Dendi_Token_Key_Bca_Log");			
			log.setKeterangan("GAGAL UPDATE DATA TOKEN BERDASARKAN SERIAL NUMBER = "+objToken.getSerialNumber());

		}		
		//SAVE KE TABEL DENDI_TOKEN_KEY_BCA_LOG
		log = logTokenKeyBcaRepository.save(log);
		
		return response;
	}

}
