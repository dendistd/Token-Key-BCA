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
import com.example.token.payload.cabang.InputCreateCabang;
import com.example.token.payload.cabang.InputDeleteCabang;
import com.example.token.payload.cabang.InputGetCabangByStatus;
import com.example.token.payload.cabang.InputUpdateCabang;
import com.example.token.payload.cabang.InputUpdateStatusCabang;
import com.example.token.payload.cabang.ResponseCreateCabang;
import com.example.token.payload.cabang.ResponseDataCabang;
import com.example.token.payload.cabang.ResponseUpdateNamaCabang;
import com.example.token.payload.cabang.ResponseUpdateCabang;
import com.example.token.repository.CabangRepo;
import com.example.token.service.CabangService;

@Service
public class CabangServiceImple implements CabangService {
	@Autowired
	CabangRepo cabangRepo;
	
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
	public List<Cabang> getCabangByStatus (InputGetCabangByStatus input){
		String valueLog = logNumber();
		List<Cabang> list = new ArrayList<>();
		list = cabangRepo.findByStatus(input.getStatus().toUpperCase());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data");
		return list;
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
//				return response;
			}
		}
		//TIDAK BISA UPDATE-> STATUS DB SAMA DENGAN INPUT
		else {
			response.setStatusNew("No Update");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Status DB SAMA Dengan Input");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi Response = " + response.toString());
//			return response;
		}
		
		//BISA UPDATE-> NAMA CABANG DB BEDA DENGAN INPUT
		if(!(findData.getNamaCabang().equalsIgnoreCase(input.getNamaCabang()) )) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nama Cabang DB BEDA Dengan Input");
			findData.setNamaCabang(input.getNamaCabang().toUpperCase());
//			penanda[1] = "Terupdate";
			response.setNamaCabangNew(findData.getNamaCabang().toUpperCase());
		}
		//TDK UPDATE -> NAMA CABANG DB SAMA DENGAN INPUT
		else {

			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nama Cabang DB SAMA Dengan Input");
			response.setNamaCabangNew("No Update");
//			return response;
		}
		
		//SAVE DB 
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - isi findData = " + findData.toString());
		findData = cabangRepo.save(findData);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Save DB Berhasil"+ findData.toString());
		
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - isi response = " + response.toString());
		return response;
		
	}
	
	
	

}
