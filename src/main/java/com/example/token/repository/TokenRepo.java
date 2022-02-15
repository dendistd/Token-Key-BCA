package com.example.token.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.token.model.TokenModel;

@Repository
public interface TokenRepo extends JpaRepository<TokenModel, String>{
	
	//CEK SERIALNUMBER SUDAH ADA ATAU BELUM DALAM DB
	public Boolean existsBySerialNumber(String serialNumber);
	
	//METHODE DELETE BY SERIALNUMBER -> UPDATE cabang = "HAPUS"
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update dendi_token t set t.status = 'HAPUS' where t.serial_number = :serialNumber", nativeQuery = true)
	public void deleteBySerialNumber (String serialNumber);
	
	public TokenModel findBySerialNumber(String serialNumber);
	
	public List<TokenModel> findByStatus(String status);
	
	//FUNGSI UNTUK DAPATKAN KODE_CABANG DAN NAMA_CABANG UNTUK KEPERLUAN UPDATE VALUE CABANG DI TBL DENDI_TOKEN
	@Query(value = "select c.kode_cabang, c.nama_cabang from dendi_cabang c where c.kode_cabang = :kodeCabang ", nativeQuery = true)
	public Map<String, String> getNamaCabangByKodeCabang(String kodeCabang);
	
	//Boolean Untuk Cabang IS Null
//	@Query(value = "select t.cabang from dendi_token t where t.serial_number = :serialNumber and t.cabang is null", nativeQuery = true)
//	public Map<String, String> isCabangNull(String serialNumber);
	@Query(value = "select t.cabang from dendi_token t where t.serial_number = :serialNumber and t.cabang is null", nativeQuery = true)
	public Boolean isCabangNull (String serialNumber);
}
