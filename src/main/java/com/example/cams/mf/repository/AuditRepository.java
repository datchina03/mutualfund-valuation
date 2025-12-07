package com.example.cams.mf.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.cams.mf.model.TransactionDetails;

public interface AuditRepository extends CrudRepository<TransactionDetails, Long> {

}
