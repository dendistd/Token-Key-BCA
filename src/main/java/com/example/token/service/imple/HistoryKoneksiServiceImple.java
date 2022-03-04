package com.example.token.service.imple;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.token.model.HistoryKoneksi;
import com.example.token.model.Koneksi;
import com.example.token.model.LogTokenKeyBca;
import com.example.token.payload.historykoneksi.ResponseCreateHistoryKoneksi;
import com.example.token.payload.koneksi.InputCreateKoneksi;
import com.example.token.payload.koneksi.ResponseCreateKoneksi;
import com.example.token.repository.HistoryKoneksiRepo;
import com.example.token.repository.KoneksiRepo;
import com.example.token.repository.LogTokenKeyBcaRepository;
import com.example.token.service.HistoryKoneksiService;
import com.example.token.service.KoneksiService;

@Service
public class HistoryKoneksiServiceImple implements HistoryKoneksiService {
	@Autowired
	HistoryKoneksiRepo historyKoneksiRepo;
	@Autowired
	LogTokenKeyBcaRepository logTokenKeyBcaRepository;
	
	@Autowired
	KoneksiRepo koneksiRepo;
	
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
	
	public void createHistoryKoneksi (InputCreateKoneksi input) {
		String valueLog = logNumber();
		HistoryKoneksi hk = new HistoryKoneksi();
		LogTokenKeyBca log = new LogTokenKeyBca();
		hk.setId(RandomStringUtils.randomAlphanumeric(20).toUpperCase());
		hk.setSerialNumber(input.getSerialNumber());
		hk.setUserId(input.getUserId());
		hk.setStatusAwal("BARU");
		hk.setStatusAkhir("AKTIF");
		
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setIdData(hk.getId().toUpperCase());
		log.setTabel("HISTORY_KONEKSI");
		log.setTindakan("CREATE");

		if(koneksiRepo.cekSNKoneksi(input.getSerialNumber().toUpperCase()).get("status").equals("BARU")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - HK -> Kondisi SN Token Status = 'BARU'");
			hk.setKeterangan("KONEKSI BARU");
			log.setKeterangan("BERHASIL CREATE DATA HISTORY KONEKSI BERDASARKAN SERIAL NUMBER = "+hk.getSerialNumber().toUpperCase());
		}
		
		if(koneksiRepo.cekSNKoneksi(input.getSerialNumber().toUpperCase()).get("status").equals("AKTIF")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - HK-> Kondisi SN Token Status = 'AKTIF'");
			hk.setKeterangan("TAMBAH KONEKSI");
			log.setKeterangan("BERHASIL CREATE DATA HISTORY KONEKSI BERDASARKAN SERIAL NUMBER = "+hk.getSerialNumber().toUpperCase());
		}
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - HK -> Save ke DB selesai, Isi HK = "+hk.toString());
		hk = historyKoneksiRepo.save(hk);
		log = logTokenKeyBcaRepository.save(log);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - HK -> Log berhasil di save ke DB, isi Log = "+log.toString());
		
	}
}
