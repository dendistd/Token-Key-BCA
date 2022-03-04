package com.example.token.service.imple;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.token.model.Cabang;
import com.example.token.model.LogTokenKeyBca;
import com.example.token.payload.cabang.InputCreateCabang;
import com.example.token.payload.cabang.InputDeleteCabang;
import com.example.token.payload.cabang.InputUpdateCabang;
import com.example.token.payload.cabang.InputUpdateStatusCabang;
import com.example.token.payload.cabang.OutputGetCabang;
import com.example.token.payload.cabang.ResponseCreateCabang;
import com.example.token.payload.cabang.ResponseDataCabang;
import com.example.token.payload.cabang.ResponseUpdateNamaCabang;
import com.example.token.payload.cabang.ResponseUpdateCabang;
import com.example.token.repository.CabangRepo;
import com.example.token.repository.LogTokenKeyBcaRepository;
import com.example.token.service.CabangService;

@Service
public class CabangServiceImple implements CabangService {
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
	
	//GET CABANG BY STATUS 
	public List<OutputGetCabang> getCabangByStatus(String input){
		String valueLog = logNumber();
		List<Cabang> list = new ArrayList<>();
		List<OutputGetCabang> result = new ArrayList<>();
		list = cabangRepo.findByStatus(input.toUpperCase());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data");
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTindakan("SELECT");
		log.setTabel("DENDI_CABANG");	
		//convert 
		for(Cabang cb : list) {
			OutputGetCabang output = new OutputGetCabang(cb.getKodeCabang(), cb.getNamaCabang(), cb.getStatus());
			result.add(output);
		}
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - isi result = "+result.toString());
		//KETERANGAN BERHASIL -> INSERT DATA KE TABEL DENDI_TOKEN_KEY_BCA_LOG
		if(result.size() != 0) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");	
			log.setKeterangan("BERHASIL, SELECT DATA CABANG BERDASARKAN STATUS =" + input.toUpperCase());
		}
		//KETERANGAN GAGAL -> INSERT DATA KE TABEL DENDI_TOKEN_KEY_BCA_LOG
		if(result.size() == 0) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("GAGAL, SELECT DATA CABANG BERDASARKAN STATUS =" + input.toUpperCase());	
		
		}
		log = logTokenKeyBcaRepository.save(log); 
		return result;
	}
	
	//CREATE 
	public ResponseCreateCabang createCabang (InputCreateCabang input) {
		String valueLog = logNumber();
		Cabang cabang = new Cabang(input.getKodeCabang().toUpperCase(), input.getNamaCabang().toUpperCase(), input.getStatus().toUpperCase());
		ResponseCreateCabang response = new ResponseCreateCabang();
		LogTokenKeyBca log = new LogTokenKeyBca();
		
		//INSERT TABEL DENDI_TOKEN_KEY_BCA_LOG
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setIdData(cabang.getKodeCabang());
		log.setTabel("DENDI_CABANG");
		log.setTindakan("CREATE");
		//Bisa Create-> Kode_cabang Belum Ada DI DB
		if(!cabangRepo.existsById(input.getKodeCabang().toUpperCase()) ){
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kode Cabang Belum Ada Dalam DB");
			if(input.getStatus().equalsIgnoreCase("aktif")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status = AKTIF");
				cabang = cabangRepo.save(cabang);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Save Ke DB");
				//INSERT DATA KE TABEL LOG -> KETERANGAN "BERHASIL"
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - INSERT DATA TABEL DENDI_LOG -> KETERANGAN BERHASIL");
				log.setKeterangan("BERHASIL, INSERT DATA CABANG BERDASARKAN KODE_CABANG = "+cabang.getKodeCabang());
				
			}
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status BUKAN AKTIF");
				log.setKeterangan("GAGAL, INSERT DATA CABANG BERDASARKAN KODE_CABANG = "+cabang.getKodeCabang());
				cabang.setStatus("");
			}
		}
		//TIDAK BISA CREATE -> Kode Cabang Sudah Ada Dalam DB
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kode Cabang SUDAH Ada Dalam DB");
			log.setKeterangan("GAGAL, INSERT DATA CABANG BERDASARKAN KODE_CABANG = "+cabang.getKodeCabang());
			cabang.setKodeCabang("");
		}
		//SAVE KE TABEL DENDI_TOKEN_KEY_BCA_LOG
		log = logTokenKeyBcaRepository.save(log);
		
		response.setKodeCabang(cabang.getKodeCabang());
		response.setNamaCabang(cabang.getNamaCabang());
		response.setSerialNumber(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
		response.setStatus(cabang.getStatus());
		return response;
	}
	
	//DELETE
	public ResponseDataCabang deleteCabang (InputDeleteCabang input) {
		String valueLog = logNumber();
		LogTokenKeyBca log = new LogTokenKeyBca();
		ResponseDataCabang responGagal = new ResponseDataCabang();
		//INSERT DATA KE TABEL DENDI_TOKEN_LOG
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTabel("DENDI_CABANG");
		log.setTindakan("DELETE");
		//KODE_CABANG TIDAK ADA DALAM DB
		if(!cabangRepo.existsById(input.getKodeCabang().toUpperCase())) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kode Cabang Tidak Ada Dalam DB");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN 'GAGAL' ->Insert Data Tabel Dendi_Token_Key_Bca_Log");	
			log.setKeterangan("GAGAL DELETE DATA CABANG, KODE_CABANG TIDAK ADA DALAM DB, KODE_CABANG = "+input.getKodeCabang().toUpperCase());
			//SAVE KE TABEL DENDI_TOKEN_KEY_BCA_LOG
			log = logTokenKeyBcaRepository.save(log);
			responGagal.setKodeCabang("");
			return responGagal;
		}
		
		Cabang findData = cabangRepo.findById(input.getKodeCabang().toUpperCase()).get();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Ditemukan");
		ResponseDataCabang response = new ResponseDataCabang(findData.getKodeCabang(), findData.getNamaCabang(), findData.getStatus());
		log.setIdData(findData.getKodeCabang());
		
		//BERHASIL HAPUS
		if(!findData.getStatus().equalsIgnoreCase("hapus")) {
			findData.setStatus("HAPUS");
			findData = cabangRepo.save(findData);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Sudah Terhapus");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN 'BERHASIL' ->Insert Data Tabel Dendi_Token_Key_Bca_Log");	
			log.setKeterangan("BERHASIL, DELETE DATA CABANG BERDASARKAN KODE_CABANG = "+findData.getKodeCabang());
			//SAVE KE TABEL DENDI_TOKEN_KEY_BCA_LOG
			log = logTokenKeyBcaRepository.save(log);
		}
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Menghapus Data Yang Sudah Terhapus");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN 'GAGAL' ->Insert Data Tabel Dendi_Token_Key_Bca_Log");	
			log.setKeterangan("GAGAL, DELETE DATA CABANG BERDASARKAN KODE_CABANG = "+findData.getKodeCabang());
			//SAVE KE TABEL DENDI_TOKEN_KEY_BCA_LOG
			log = logTokenKeyBcaRepository.save(log);
			response.setStatus("");
			return response;
		}	
		response.setStatus(findData.getStatus());
		return response;
	}
	
	//UPDATE  CABANG
	public ResponseUpdateCabang updateCabang (InputUpdateCabang input) {
		String valueLog = logNumber();
		ResponseUpdateCabang responGagal = new ResponseUpdateCabang();
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTabel("DENDI_CABANG");
		log.setTindakan("UPDATE");
		
		//JIKA KODE_CABANG TIDAK ADA DALAM DB
		if(!cabangRepo.existsById(input.getKodeCabang().toUpperCase())) {
			responGagal.setKodeCabang("");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN 'GAGAL'-> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("GAGAL UPDATE DATA CABANG BERDASARKAN KODE_CABANG = "+input.getKodeCabang());
			log = logTokenKeyBcaRepository.save(log);
			return responGagal;
		}
		
		Cabang findData = cabangRepo.findById(input.getKodeCabang().toUpperCase()).get();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data Cabang");
		ResponseUpdateCabang response = new ResponseUpdateCabang(findData.getKodeCabang(), findData.getNamaCabang(), findData.getStatus());
		String[] penanda = new String[2];
		
		log.setIdData(findData.getKodeCabang());
		
		//BISA UPDATE-> INPUT STATUS DAN STATUS CABANG DI DB BEDA
		if(!(findData.getStatus().equalsIgnoreCase(input.getStatus()) )) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Status DB BEDA Dengan Input");
			//BISA UPDATE STATUS SESUAI KONDISI SOAL-> AKTIF, TDK AKTIF
			if(input.getStatus().equalsIgnoreCase("aktif") ||  input.getStatus().equalsIgnoreCase("tdk aktif")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status Memenuhi Kondisi");
				penanda[0] = "Terupdate";
				response.setStatusNew(input.getStatus().toUpperCase());
			}
			//Tidak Bisa Update -> Value Status Tidak Memenuhi
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Update Status Cabang");
				response.setStatusNew("");
				penanda[0] = "Can't Update";
			}
		}
		//TIDAK BISA UPDATE-> STATUS DB SAMA DENGAN INPUT
		else {
			response.setStatusNew("No Update");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Status DB SAMA Dengan Input");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi Response = " + response.toString());
			penanda[0] = "No Update";
		}
		
		//BISA UPDATE-> NAMA CABANG DB BEDA DENGAN INPUT
		if(!findData.getNamaCabang().equalsIgnoreCase(input.getNamaCabang()) ) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nama Cabang DB BEDA Dengan Input");
			//NAMA CABANG BUKAN STRING KOSONG 
			if(!input.getNamaCabang().equals("") && !input.getNamaCabang().startsWith(" ")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nama Cabang BUKAN String Kosong");
				response.setNamaCabangNew(input.getNamaCabang().toUpperCase());
				penanda[1] = "Terupdate";
			}
			//GAGAL UPDATE -> NAMA CABANG BERNILAI STRING KOSONG ""
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nama Cabang BERNILAI String Kosong");
				response.setNamaCabangNew("Can't Update");
				penanda[1] = "Can't Update";
			}
		}
		//TDK UPDATE -> NAMA CABANG DB SAMA DENGAN INPUT
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nama Cabang DB SAMA Dengan Input");
			response.setNamaCabangNew("No Update");
			penanda[1] = "No Update";
		}
		
		//SAVE DB 
		//BISA UPDATE JIKA KEDUA FIELD MEMENUHI KONDISI UNTUK UPDATE
		if(!penanda[0].equals("Can't Update") && !penanda[1].equals("Can't Update")) {
			//TIDAK ADA PERUBAHAN DATA
			if(penanda[0].equals("No Update") && penanda[1].equals("No Update")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - TIDAK ADA UPDATE DATA");
				log.setKeterangan("GAGAL UPDATE DATA CABANG BERDASARKAN KODE_CABANG = "+findData.getKodeCabang());
			}
			//BISA SAVE DATA UPDATE
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Masuk Blok Bisa Save Data Update");
				findData.setNamaCabang(input.getNamaCabang().toUpperCase());
				findData.setStatus(input.getStatus().toUpperCase()); 
				findData = cabangRepo.save(findData);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Save DB Berhasil"+ findData.toString());
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - isi findData = " + findData.toString());
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN 'BERHASIL'-> Insert Data Tabel Dendi_Token_Key_Bca_Log");	
				log.setKeterangan("BERHASIL UPDATE DATA CABANG BERDASARKAN KODE_CABANG = "+findData.getKodeCabang());
			}
		}
		else {	
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN 'GAGAL'-> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("GAGAL UPDATE DATA CABANG BERDASARKAN KODE_CABANG = "+findData.getKodeCabang());
		}
		
		//SAVE KE DENDI_TOKEN_KEY_BCA_LOG
		log = logTokenKeyBcaRepository.save(log);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - isi Response = " + response.toString());
		return response;
	}

}
