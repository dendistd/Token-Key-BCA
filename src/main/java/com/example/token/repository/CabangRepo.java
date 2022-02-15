package com.example.token.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.token.model.Cabang;

@Repository
public interface CabangRepo extends JpaRepository<Cabang, String> {
	//GET CABANG BY STATUS
	public List<Cabang> findByStatus(String status);
	
	//Proses Menggab
	
}
