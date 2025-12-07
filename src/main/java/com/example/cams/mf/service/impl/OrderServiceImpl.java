package com.example.cams.mf.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cams.mf.model.OrderRequest;
import com.example.cams.mf.model.FundsDetails;
import com.example.cams.mf.model.TransactionDetails;
import com.example.cams.mf.model.UserFundsSummary;
import com.example.cams.mf.repository.AuditRepository;
import com.example.cams.mf.repository.FundsRepository;
import com.example.cams.mf.repository.OrderRepository;
import com.example.cams.mf.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	FundsRepository fundRepository;
	
	@Autowired
	AuditRepository auditRepository;

	
	@Override
	@Transactional
	public void executeOrder(OrderRequest buyOrderRequest) {
		
		try {
			Optional<FundsDetails> dbFundDetails=fundRepository.findNavByFundId(buyOrderRequest.fundId());
			BigDecimal navDB=dbFundDetails.map(FundsDetails::getNav).orElseThrow(()->new RuntimeException("Fund is not present"));
			
			if(navDB.compareTo(buyOrderRequest.userNav())==0) {
				var userId=buyOrderRequest.userId();
				var fundId=buyOrderRequest.fundId();
			UserFundsSummary userHoldings=orderRepository.findByUserIdAndFundId(userId, fundId).orElseGet(()->{
				UserFundsSummary uf=new UserFundsSummary();
				uf.setFundId(fundId);
				uf.setUserId(userId);
				uf.setUnitsOwned(BigDecimal.ZERO);
				uf.setTotalUnitsValue(BigDecimal.ZERO);
				return uf;
			});
			if (buyOrderRequest.orderType().equalsIgnoreCase("buy")) {
				var newUnitsHoldings = userHoldings.getUnitsOwned().add(buyOrderRequest.orderUnits());
				userHoldings.setUnitsOwned(newUnitsHoldings);
				BigDecimal totalValue = newUnitsHoldings.multiply(navDB);
				userHoldings.setTotalUnitsValue(totalValue);
			} else if (buyOrderRequest.orderType().equalsIgnoreCase("redeem")) {
				var newUnitsHoldings = userHoldings.getUnitsOwned().subtract(buyOrderRequest.orderUnits());
				userHoldings.setUnitsOwned(newUnitsHoldings);
				BigDecimal totalValue = newUnitsHoldings.multiply(navDB);
				userHoldings.setTotalUnitsValue(totalValue);
			}
			orderRepository.save(userHoldings);
			TransactionDetails transactionDetails=new TransactionDetails();
			transactionDetails.setDateOfTranasaction(LocalDate.now(ZoneId.of("Asia/Kolkata")));
			transactionDetails.setUserId(buyOrderRequest.userId());
			transactionDetails.setFundId(buyOrderRequest.fundId());
			transactionDetails.setOrderType(buyOrderRequest.orderType());
			transactionDetails.setTransactionUnits(buyOrderRequest.orderUnits());
			auditRepository.save(transactionDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
