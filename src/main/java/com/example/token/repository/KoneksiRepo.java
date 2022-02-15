package com.example.token.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.token.model.Koneksi;
@Repository
public interface KoneksiRepo extends JpaRepository<Koneksi, String> {
	//FIND KONEKSI BY USER ID-> Untuk kebutuhan Delete Koneksi By User Id
	public Koneksi findByUserId (String userId);
	
	public Boolean existsByUserId(String userId);
	
	//CEK SN DAN STATUS DARI TABEL DENDI_TOKEN UNTUK KEBUTUHAN CREATE DATA KONEKSI
	@Query(value = "select t.serial_number, t.status, t.cabang from dendi_token t where t.serial_number = :serialNumber and (t.status = 'AKTIF' OR t.status = 'BARU')", nativeQuery = true)
	public Map<String, String> cekSNKoneksi (String serialNumber);

	//Find Koneksi By Status
	public List<Koneksi> findByStatus (String status);
	

}
