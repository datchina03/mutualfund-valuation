package com.example.cams.mf.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="FUNDS_DETAILS")
@Data
public class FundsDetails {
	
	@Id
	private long fundId;
	
	@Column(name="FUND_NAME")
	private String fundName;
	
	@Column(name="NAV")
	private BigDecimal nav;

	@Column(name="DATE")
	private LocalDate date;
}
