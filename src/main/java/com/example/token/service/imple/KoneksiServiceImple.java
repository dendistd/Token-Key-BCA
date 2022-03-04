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
import com.example.token.model.HistoryKoneksi;
import com.example.token.model.Koneksi;
import com.example.token.model.LogTokenKeyBca;
import com.example.token.model.TokenModel;
import com.example.token.payload.koneksi.InputCreateKoneksi;
import com.example.token.payload.koneksi.InputDeleteKoneksi;
import com.example.token.payload.koneksi.InputUpdateKoneksi;
import com.example.token.payload.koneksi.ResponseCreateKoneksi;
import com.example.token.payload.koneksi.ResponseDataKoneksi;
import com.example.token.payload.koneksi.ResponseUpdateKoneksi;
import com.example.token.payload.token.InputUpdateToken;
import com.example.token.payload.token.ResponseUpdateToken;
import com.example.token.repository.CabangRepo;
import com.example.token.repository.HistoryKoneksiRepo;
import com.example.token.repository.KoneksiRepo;
import com.example.token.repository.LogTokenKeyBcaRepository;
import com.example.token.repository.TokenRepo;
import com.example.token.service.HistoryKoneksiService;
import com.example.token.service.KoneksiService;
import com.example.token.service.TokenService;

@Service
public class KoneksiServiceImple implements KoneksiService {
	@Autowired
	KoneksiRepo koneksiRepo;
	
	@Autowired
	HistoryKoneksiRepo historyKoneksiRepo;
	
	@Autowired
	CabangRepo cabangRepo;
	
	@Autowired
	TokenRepo tokenRepo;
	
	@Autowired
	LogTokenKeyBcaRepository logTokenKeyBcaRepository;
		
	@Autowired
	HistoryKoneksiService historyKoneksiService;
	
	@Autowired
	TokenService tokenService;
	
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
	
	//FIND KONEKSI BY STATUS
	public List<Koneksi> findKoneksiByStatus(String input){
		String valueLog = logNumber();
		List<Koneksi> list = koneksiRepo.findByStatus(input.toUpperCase());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Ditemukan");
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTabel("DENDI_KONEKSI");
		log.setTindakan("SELECT");
		//KETERANGAN BERHASIL -> INSERT DENDI_TOKEN_KEY_BCA	
		if(list.size() != 0) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("BERHASIL SELECT DATA KONEKSI BERDASARKAN STATUS = "+input.toUpperCase());
			
		}
		//KETERANGAN GAGAL -> INSERT DENDI_TOKEN_KEY_BCA
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("GAGAL SELECT DATA KONEKSI BERDASARKAN STATUS = "+input.toUpperCase());
		}
		log = logTokenKeyBcaRepository.save(log);
		return list;
	}
	
	//DELETE KONEKSI BY USER_ID
	public ResponseDataKoneksi deleteKoneksi(InputDeleteKoneksi input) {
		String valueLog = logNumber();
		//KONDISI USERID TIDAK ADA DALAM DB
		ResponseDataKoneksi responGagal = new ResponseDataKoneksi();
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTabel("DENDI_KONEKSI");
		log.setTindakan("DELETE");
		if(!koneksiRepo.existsByUserId(input.getUserId().toUpperCase())) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - USER ID TIDAK DITEMUKAN");
			responGagal.setId("");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");		
			log.setKeterangan("GAGAL DELETE DATA KONEKSI BERDASARKAN USER ID = "+input.getUserId().toUpperCase());
			log = logTokenKeyBcaRepository.save(log);
			return responGagal;
		}
		
		Koneksi findData = koneksiRepo.findByUserId(input.getUserId().toUpperCase());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Ditemukan");
		/* String id, Date dateKoneksi, String serialNumber, String userId, String jenisKoneksi,
			String status */
		ResponseDataKoneksi response = new ResponseDataKoneksi(findData.getId(), findData.getDateKoneksi(), findData.getSerialNumber(), findData.getUserId(), findData.getJenisKoneksi(), findData.getStatus());
		log.setIdData(findData.getId());
		
		//Delete Data
		koneksiRepo.delete(findData);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil Dihapus");
		//KETERANGAN BERHASIL -> INSERT TABEL DENDI_TOKEN_KEY_BCA_LOG
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");		
		log.setKeterangan("BERHASIL DELETE DATA KONEKSI BERDASARKAN USER ID = "+findData.getUserId());
		log = logTokenKeyBcaRepository.save(log);
		
		return response;
	}
	
	//CREATE KONEKSI
	public ResponseCreateKoneksi createKoneksi (InputCreateKoneksi input) {
		String valueLog = logNumber();
		Koneksi koneksi = new Koneksi();
		ResponseCreateKoneksi response = new ResponseCreateKoneksi();
//		HistoryKoneksi hk = new HistoryKoneksi();
		TokenModel objToken = new TokenModel();
		ResponseUpdateToken objToken2 = new ResponseUpdateToken();
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setTabel("DENDI_KONEKSI");
		log.setTindakan("CREATE");

		//SN TIDAK ADA DALAM DB
		if(tokenRepo.existsBySerialNumber(input.getSerialNumber().toUpperCase()) == false) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN TIDAK ADA Dalam DB");
			response.setId("null");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("GAGAL CREATE DATA KONEKSI BERDASARKAN SERIAL NUMBER = "+input.getSerialNumber());
			log = logTokenKeyBcaRepository.save(log);
			return response;
		}
		//KODE CABANG TIDAK ADA DALAM DENDI_CABANG
		if(!cabangRepo.existsById(input.getKodeCabang().toUpperCase())) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kode Cabang Tidak Ada Dalam DB");
			response.setId("none");
			response.setCabang("");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("GAGAL CREATE DATA KONEKSI BERDASARKAN SERIAL NUMBER = "+input.getSerialNumber());
			log = logTokenKeyBcaRepository.save(log);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi Response = "+response.toString());
			return response;
		}
		
		//CEK KONDISI SESUAI PROSES BISNIS
		if(koneksiRepo.cekSNKoneksi(input.getSerialNumber().toUpperCase()).size() > 0 && input.getStatus().equalsIgnoreCase("aktif") && !koneksiRepo.existsByUserId(input.getUserId()) && cabangRepo.findById(input.getKodeCabang().toUpperCase()).get().getStatus().equals("AKTIF")) {

			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Input Param Memenuhi Syarat");
			koneksi.setId(RandomStringUtils.randomAlphanumeric(20).toUpperCase());
			koneksi.setSerialNumber(input.getSerialNumber().toUpperCase());
			koneksi.setStatus(input.getStatus().toUpperCase());
			koneksi.setUserId(input.getUserId().toUpperCase());
			Date date = new Date();
			koneksi.setDateKoneksi(date);
		
			log.setIdData(koneksi.getId());
			
			//JIKA STATUS SN TOKEN -> "AKTIF" 
			if(koneksiRepo.cekSNKoneksi(input.getSerialNumber().toUpperCase()).get("status").equals("AKTIF")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kondisi SN Token Status = 'AKTIF'");
				koneksi.setJenisKoneksi("TAMBAH");
				historyKoneksiService.createHistoryKoneksi(input);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - STATUS 'AKTIF' -> History Koneksi Service Telah Dipanggil");				
				objToken = tokenRepo.findBySerialNumber(input.getSerialNumber().toUpperCase());
				response.setCabang(objToken.getCabang().toUpperCase());
			}
			//JIKA STATUS SN TOKEN -> "BARU"
			if(koneksiRepo.cekSNKoneksi(input.getSerialNumber().toUpperCase()).get("status").equals("BARU")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kondisi SN Token Status = 'BARU'");
				koneksi.setJenisKoneksi("BARU");
				historyKoneksiService.createHistoryKoneksi(input);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - STATUS 'BARU' ->History Koneksi Service Telah Dipanggil");

				//PANGGIL UPDATE TOKENSERVICE				
				InputUpdateToken inputUpdateToken = new InputUpdateToken();
				inputUpdateToken.setSerialNumber(input.getSerialNumber().toUpperCase());
				inputUpdateToken.setCabang(input.getKodeCabang().toUpperCase());
				inputUpdateToken.setStatus(input.getStatus().toUpperCase());
				objToken2 = tokenService.updateTokenCabang(inputUpdateToken);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - STATUS SN 'BARU' ->UpdateToken Service Telah Dipanggil");
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - ISI OBJTOKEN2 = "+objToken2.toString());
				
				response.setCabang(objToken2.getCabangNew().toUpperCase());
	
			}
			//SAVE KONEKSI KE DB
			koneksi = koneksiRepo.save(koneksi);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Save Koneksi Ke DB = "+koneksi.toString());
		
			//KETERANGAN BERHASIL -> INSERT DENDI_TOKEN_KEY_BCA_LOG			
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("BERHASIL CREATE DATA KONEKSI BERDASARKAN SERIAL NUMBER = "+koneksi.getSerialNumber());
		}
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Input Param Tidak Memnuhi Syarat");
			koneksi.setId("");
			koneksi.setSerialNumber("");
			koneksi.setStatus("");
			koneksi.setUserId("");
			koneksi.setDateKoneksi(null);
			koneksi.setJenisKoneksi("");
			//KETERANGAN GAGAL -> INSERT DENDI_TOKEN_KEY_BCA_LOG			
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL -> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan("GAGAL CREATE DATA KONEKSI BERDASARKAN SERIAL NUMBER = "+input.getSerialNumber());
		}
		//SAVE KE TABEL DENDI_TOKEN_KEY_BCA_LOG
		log = logTokenKeyBcaRepository.save(log);

		response.setId(koneksi.getId());
		response.setDateKoneksi(koneksi.getDateKoneksi());
		response.setJenisKoneksi(koneksi.getJenisKoneksi());
		response.setSerialNumber(koneksi.getSerialNumber());
		response.setStatus(koneksi.getStatus());		
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi Response = "+response.toString());
		return response;	
	}
	
	//UPDATE KONEKSI 
	public ResponseUpdateKoneksi updateKoneksi (InputUpdateKoneksi input) {
		/* 	a.	pastikan serialNumber tersedia di tabel token dengan status BARU atau AKTIF
			b.	status koneksi bernilai AKTIF
			c.	Date bernilai tanggal dan waktu saat transaksi terjadi
			d.	apabila status SN Token BARU maka jenis koneksi bernilai Baru dan lakukan update nilai cabang di tabel token.  
			apabila status SN Token AKTIF maka jenis koneksi bernilai Tambah.
			e.	Jika semua kondisi terpenuhi maka lakukan simpan data koneksi. Jika ada kondisi tidak terpenuhi maka batal simpan data.
			f.	Insert data history dengan ketentuan sebagai berikut
				Status awal bernilai BARU. Status akhir bernilai AKTIF. Keterangan bernilai GANTI TOKEN
				Apabila insert data history gagal maka batal update data koneksi.
			g.	Kirim error schema dan output schema sesuai dengan kondisi dan isi tabel.
		 */
		String valueLog = logNumber();		
		Koneksi findData = koneksiRepo.findById(input.getId().toUpperCase()).get();
		HistoryKoneksi hk = new HistoryKoneksi();
		TokenModel objToken = new TokenModel();
		String[] penanda = new String[2];
		
		LogTokenKeyBca log = new LogTokenKeyBca();
		log.setId(valueLog);
		log.setCreateDate(new Date());
		log.setIdData(findData.getId());
		log.setTabel("DENDI_KONEKSI");
		log.setTindakan("UPDATE");		
		/* String id, Date dateKoneksi, String serialNumber, String userId, String jenisKoneksi,
			String status */
		ResponseUpdateKoneksi response = new ResponseUpdateKoneksi(findData.getId(), findData.getDateKoneksi(), findData.getSerialNumber()
				, findData.getUserId(), findData.getJenisKoneksi(), findData.getStatus());
		
		//KONDISI SESUAI PROSES BISNIS
		if(koneksiRepo.cekSNKoneksi(input.getSerialNumber().toUpperCase()).size() > 0 && input.getStatus().equalsIgnoreCase("aktif")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Input Param Memenuhi Syarat");

			//BISA UPDATE -> SN DB BEDA SAMA INPUT
			if(!(findData.getSerialNumber().equalsIgnoreCase(input.getSerialNumber()))) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN DB BEDA dengan Input ");
				
				//SN TOKEN STATUS -> "BARU" 
				if(koneksiRepo.cekSNKoneksi(input.getSerialNumber().toUpperCase()).get("status").equals("BARU")) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kondisi SN Token Status = 'BARU'");
					findData.setSerialNumber(input.getSerialNumber().toUpperCase());
					findData.setJenisKoneksi("BARU");
					penanda[0] = "Update";
					/*UPDATE VALUE CABANG DI TABEL DENDI_TOKEN */
					//find Data Token Berdasarkan Input Param SN
					objToken = tokenRepo.findBySerialNumber(input.getSerialNumber().toUpperCase());
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Token Berdasarkan Input Param SN DItemukan");					
				}
				
				//SN TOKEN STATUS -> "AKTIF"
				if(koneksiRepo.cekSNKoneksi(input.getSerialNumber().toUpperCase()).get("status").equals("AKTIF")) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kondisi SN Token Status = 'AKTIF'");
					findData.setSerialNumber(input.getSerialNumber().toUpperCase());
					findData.setJenisKoneksi("TAMBAH");
					penanda[0] = "Update";	
				}
			}
			//SN DB SAMA DENGAN INPUT 
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - SN DB SAMA dengan Input ");
				response.setSerialNumberNew("");
				penanda[0] = "No Update";
			}
			
			//BISA UPDATE -> STATUS DB BEDA SAMA INPUT
			if(!findData.getStatus().equalsIgnoreCase(input.getStatus())) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - STATUS DB BEDA dengan Input ");
				findData.setStatus(input.getStatus().toUpperCase());
				penanda[1] = "Update";
			}
			// STATUS DB SAMA DENGAN INPUT
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - STATUS DB SAMA dengan Input ");
				response.setStatusNew("");
				penanda[1] = "No Update";
			}
			
			//SAVE KE DB JIKA TERDAPAT PERUBAHAN DATA
			if(!penanda[0].equals("No Update") || !penanda[1].equals("No Update")) {
				//INSERT DATA KE TABEL DENDI_HISTORY_KONEKSI
				hk.setId(RandomStringUtils.randomAlphanumeric(20).toUpperCase());
				hk.setSerialNumber(findData.getSerialNumber());
				hk.setUserId(findData.getUserId());
				hk.setStatusAwal("BARU");
				hk.setStatusAkhir("AKTIF");
				hk.setKeterangan("GANTI TOKEN");
				hk = historyKoneksiRepo.save(hk);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Save Histtory Koneksi Ke DB");

				//SAVE KONEKSI KE DB 
				Date dateUpdate = new Date();
				findData.setDateKoneksi(dateUpdate);
				findData = koneksiRepo.save(findData);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Save ke Dendi_Koneksi ");
				
				//KETERANGAN  BERHASIL -> INSERT DATA KE TABEL DENDI_TOKEN_KEY_BCA_LOG
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN BERHASIL-> Insert Data Tabel Dendi_Token_Key_Bca_Log");
				log.setKeterangan(" BERHASIL UPDATE DATA KONEKSI BERDASARKAN ID = "+findData.getId());
				
			}
			
		}//akhir if syarat
		
		//KONDIISI TIDAK MEMENUHI SYARAT
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Kondisi Tidak Memenuhi SYarat = " + input.toString());
			response.setId("");
			
			//KETERANGAN  GAGAL -> INSERT DATA KE TABEL DENDI_TOKEN_KEY_BCA_LOG
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL-> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan(" GAGAL UPDATE DATA KONEKSI BERDASARKAN ID = "+findData.getId());	
			log = logTokenKeyBcaRepository.save(log);
			return response;	
		}
		//KONDISI NO UPDATE UNTUK SEMUA FIELD
		if(penanda[0].equals("No Update") && penanda[1].equals("No Update")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - NO UPDATE Untuk Semua Field ");
			response.setId("No Update");
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - KETERANGAN GAGAL-> Insert Data Tabel Dendi_Token_Key_Bca_Log");
			log.setKeterangan(" GAGAL UPDATE DATA KONEKSI BERDASARKAN ID = "+findData.getId());
		}
		
		if(penanda[0].equalsIgnoreCase("Update")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ada Update Untuk SN ");
			response.getSerialNumberOld();
			response.setSerialNumberNew(findData.getSerialNumber().toUpperCase());
		}
		
		if(penanda[1].equalsIgnoreCase("Update")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ada Update Untuk Status ");
			response.getStatusOld();
			response.setStatusNew(findData.getStatus().toUpperCase());
		}
		
		log = logTokenKeyBcaRepository.save(log);
		response.setDateKoneksiNew(findData.getDateKoneksi());
		return response;	
	}
		
}
