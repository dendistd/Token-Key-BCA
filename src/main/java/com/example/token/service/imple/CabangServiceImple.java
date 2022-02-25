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
		//convert 
		for(Cabang cb : list) {
			OutputGetCabang output = new OutputGetCabang(cb.getKodeCabang(), cb.getNamaCabang(), cb.getStatus());
			result.add(output);
		}
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - isi result = "+result.toString());
		
		if(result.size() != 0) {
			//INSERT DATA KE TABEL DENDI_TOKEN_KEY_BCA_LOG
			LogTokenKeyBca log = new LogTokenKeyBca();
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Blok Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setId(valueLog);
			log.setCreateDate(new Date());
			log.setTindakan("SELECT");
			log.setKeterangan("SELECT DATA CABANG BERDASARKAN STATUS =" + input.toUpperCase());
			log.setTabel("DENDI_CABANG");	
			log = logTokenKeyBcaRepository.save(log); 
		}
		
		return result;
	}
	
	//CREATE 
	public ResponseCreateCabang createCabang (InputCreateCabang input) {
		String valueLog = logNumber();
		Cabang cabang = new Cabang(input.getKodeCabang().toUpperCase(), input.getNamaCabang().toUpperCase(), input.getStatus().toUpperCase());
		ResponseCreateCabang response = new ResponseCreateCabang();
		
		//Bisa Create-> Kode_cabang Belum Ada DI DB
		if(!cabangRepo.existsById(input.getKodeCabang().toUpperCase()) ){
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kode Cabang Belum Ada Dalam DB");
			if(input.getStatus().equalsIgnoreCase("aktif")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status = AKTIF");
				cabang = cabangRepo.save(cabang);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Save Ke DB");
				//INSERT DATA KE TABEL LOG
				LogTokenKeyBca log = new LogTokenKeyBca();
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Blok Insert Data Tabel Dendi_Token_Key_Bca_Log");
				log.setId(valueLog);
				log.setCreateDate(new Date());
				log.setIdData(cabang.getKodeCabang());
				log.setTabel("DENDI_CABANG");
				log.setTindakan("CREATE");
				log.setKeterangan("INSERT DATA CABANG BERDASARKAN KODE_CABANG = "+cabang.getKodeCabang());
				log = logTokenKeyBcaRepository.save(log);
			}
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status BUKAN AKTIF");
				
				cabang.setStatus("");
			}
		}
		//TIDAK BISA CREATE -> Kode Cabang Sudah Ada Dalam DB
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kode Cabang SUDAH Ada Dalam DB");
			cabang.setKodeCabang("");
		}
		
		response.setKodeCabang(cabang.getKodeCabang());
		response.setNamaCabang(cabang.getNamaCabang());
		response.setSerialNumber(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
		response.setStatus(cabang.getStatus());
		
		return response;
	}
	
	//DELETE
	public ResponseDataCabang deleteCabang (InputDeleteCabang input) {
		String valueLog = logNumber();
		Cabang findData = cabangRepo.findById(input.getKodeCabang().toUpperCase()).get();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Ditemukan");
		ResponseDataCabang response = new ResponseDataCabang(findData.getKodeCabang(), findData.getNamaCabang(), findData.getStatus());
		//JIKA VALUE FIELD STATUS SUDAH BERNILAI "HAPUS" -> RETURNKAN STRING ""
		if(!findData.getStatus().equalsIgnoreCase("hapus")) {
			findData.setStatus("HAPUS");
			findData = cabangRepo.save(findData);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Sudah Terhapus");
			//INSERT DATA KE TABEL LOG
			LogTokenKeyBca log = new LogTokenKeyBca();
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Blok Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setId(valueLog);
			log.setCreateDate(new Date());
			log.setIdData(findData.getKodeCabang());
			log.setTabel("DENDI_CABANG");
			log.setTindakan("DELETE");
			log.setKeterangan("DELETE DATA CABANG BERDASARKAN KODE_CABANG = "+findData.getKodeCabang());
			log = logTokenKeyBcaRepository.save(log);
		}
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Menghapus Data Yang Sudah Terhapus");
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
		//JIKA KODE_CABANG TIDAK ADA DALAM DB
		if(!cabangRepo.existsById(input.getKodeCabang().toUpperCase())) {
			responGagal.setKodeCabang("");
			return responGagal;
		}
		
		Cabang findData = cabangRepo.findById(input.getKodeCabang().toUpperCase()).get();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data Cabang");
		ResponseUpdateCabang response = new ResponseUpdateCabang(findData.getKodeCabang(), findData.getNamaCabang(), findData.getStatus());
		String[] penanda = new String[2];
		
		//BISA UPDATE-> INPUT STATUS DAN STATUS CABANG DI DB BEDA
		if(!(findData.getStatus().equalsIgnoreCase(input.getStatus()) )) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Status DB BEDA Dengan Input");
			//BISA UPDATE STATUS SESUAI KONDISI SOAL-> AKTIF, TDK AKTIF
			if(input.getStatus().equalsIgnoreCase("aktif") ||  input.getStatus().equalsIgnoreCase("tdk aktif")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Status Memenuhi Kondisi");
				findData.setStatus(input.getStatus().toUpperCase());
				penanda[0] = "Terupdate";
				response.setStatusNew(findData.getStatus().toUpperCase());
			}
			//Tidak Bisa Update -> Value Status Tidak Memenuhi
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Update Data");
				response.setStatusNew("");
			}
		}
		//TIDAK BISA UPDATE-> STATUS DB SAMA DENGAN INPUT
		else {
			response.setStatusNew("No Update");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Status DB SAMA Dengan Input");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi Response = " + response.toString());
		}
		
		//BISA UPDATE-> NAMA CABANG DB BEDA DENGAN INPUT
		if(!(findData.getNamaCabang().equalsIgnoreCase(input.getNamaCabang()) )) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nama Cabang DB BEDA Dengan Input");
			findData.setNamaCabang(input.getNamaCabang().toUpperCase());
			response.setNamaCabangNew(findData.getNamaCabang().toUpperCase());
		}
		//TDK UPDATE -> NAMA CABANG DB SAMA DENGAN INPUT
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nama Cabang DB SAMA Dengan Input");
			response.setNamaCabangNew("No Update");
		}
		
		//SAVE DB 
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - isi findData = " + findData.toString());
		
		//BISA UPDATE JIKA KEDUA FIELD MEMENUHI KONDISI UNTUK UPDATE
		if( !(response.getStatusNew().equals("") || response.getNamaCabangNew().equals("No Update") && !(response.getStatusNew().equals("No Update") || response.getNamaCabangNew().equals("No Update")) )) {
			findData = cabangRepo.save(findData);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Save DB Berhasil"+ findData.toString());
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - isi response = " + response.toString());
			//INSERT DATA KE TABEL LOG
			LogTokenKeyBca log = new LogTokenKeyBca();
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Blok Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setId(valueLog);
			log.setCreateDate(new Date());
			log.setIdData(findData.getKodeCabang());
			log.setTabel("DENDI_CABANG");
			log.setTindakan("UPDATE");
			log.setKeterangan("UPDATE DATA CABANG BERDASARKAN KODE_CABANG = "+findData.getKodeCabang());
			log = logTokenKeyBcaRepository.save(log);
		}
		return response;
	}

}
