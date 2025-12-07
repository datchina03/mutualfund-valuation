package com.example.cams.mf.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.cams.mf.model.UserFundsSummary;

public interface OrderRepository extends CrudRepository<UserFundsSummary, Long> {
	
	Optional<UserFundsSummary> findByUserIdAndFundId(long userId,long fundId);

}
