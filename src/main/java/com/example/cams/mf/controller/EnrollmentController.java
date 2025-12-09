package com.example.cams.mf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cams.mf.model.UserLoginRequest;
import com.example.cams.mf.service.EnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name="Enrollment API")
public class EnrollmentController {
	
	@Autowired
	EnrollmentService enrollmentServiceImpl;
	
	@Operation(summary="Enrollment of the user")
	@PostMapping("/register")
	public ResponseEntity<String> addUser(@Valid @RequestBody UserLoginRequest userRequest){
		enrollmentServiceImpl.addUser(userRequest);
		return ResponseEntity.ok("user added successfully");
	}

}
