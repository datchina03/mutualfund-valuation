package com.example.cams.mf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cams.mf.model.UserLoginRequest;
import com.example.cams.mf.service.EnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/register")
@Tag(name="Enrollment API")
public class EnrollmentController {
	
	@Autowired
	EnrollmentService enrollmentServiceImpl;
	
	@Operation(summary="Enrollment of the user")
	public ResponseEntity<String> addUser(@RequestBody UserLoginRequest userRequest){
		enrollmentServiceImpl.addUser(userRequest);
		return ResponseEntity.ok("user added successfully");
	}

}
