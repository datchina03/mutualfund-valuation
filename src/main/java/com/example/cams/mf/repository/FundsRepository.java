package com.example.cams.mf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cams.mf.model.FundsDetails;

public interface FundsRepository extends JpaRepository<FundsDetails, Long>{

	 public Optional<FundsDetails> findNavByFundId(Long fundId);
}

