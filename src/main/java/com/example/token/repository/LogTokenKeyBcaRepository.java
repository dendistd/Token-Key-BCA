package com.example.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.token.model.LogTokenKeyBca;
@Repository
public interface LogTokenKeyBcaRepository extends JpaRepository<LogTokenKeyBca, String> {

}
