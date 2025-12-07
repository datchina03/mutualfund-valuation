package com.example.cams.mf.service;

import com.example.cams.mf.model.OrderRequest;


public interface OrderService {
	
	void executeOrder(OrderRequest buyOrderRequest);
	
}
