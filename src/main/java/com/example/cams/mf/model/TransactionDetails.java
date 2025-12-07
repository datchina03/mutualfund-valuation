package com.example.cams.mf.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TRANSACTION_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetails {
	@Id
	@GeneratedValue
	private UUID transactionId;
	private long userId;
	private long fundId;
	private String orderType;
	private BigDecimal transactionUnits;
	private LocalDate dateOfTranasaction;

}
