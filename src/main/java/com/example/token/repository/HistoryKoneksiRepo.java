package com.example.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.token.model.HistoryKoneksi;
import com.example.token.payload.koneksi.InputCreateKoneksi;
@Repository
public interface HistoryKoneksiRepo extends JpaRepository<HistoryKoneksi, String> {
	/* g.	Insert data history dengan ketentuan sebagai berikut
			apabila status SN Token BARU maka keterangan KONEKSI BARU dan Status awal BARU. 
			apabila status SN Token AKTIF maka keterangan TAMBAH KONEKSI dan Status awal BARU.
			Status akhir bernilai AKTIF
			Apabila insert data history gagal maka batal simpan data koneksi.
*/
//	@Query(value = "insert into dendi_history_koneksi (id, serial_number, user_id, status_awal, status_akhir, keterangan) values ()", nativeQuery = true)
//	public void insertHistoryKoneksi (InputCreateKoneksi input);
}
