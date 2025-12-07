package com.example.cams.mf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cams.mf.model.OrderRequest;
import com.example.cams.mf.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name="Users API's",description = "API available for users")
public class UserContoller {

	@Autowired
	OrderService orderServiceImpl;

	@PostMapping("/order")
	@Operation(summary="users can buy mf units or redeem their mf units using this API")
	public ResponseEntity<String> executeBuyOrder(@RequestBody OrderRequest orderRequest) {
		orderServiceImpl.executeOrder(orderRequest);
		return ResponseEntity.ok().body("Success");
	}

	
}
