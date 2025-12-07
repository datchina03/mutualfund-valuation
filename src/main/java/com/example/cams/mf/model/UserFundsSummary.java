package com.example.cams.mf.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name="USER_HOLDINGS",uniqueConstraints =  @UniqueConstraint(columnNames = {"USER_ID", "FUND_ID"}))
@Data
public class UserFundsSummary {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="USER_ID")
	private long userId;
	@Column(name="FUND_ID")
	private long fundId;
	@Column(name="UNITS_OWNED")
	private BigDecimal unitsOwned;
	@Column(name="TOTAL_VALUE")
	private BigDecimal totalUnitsValue;

}
