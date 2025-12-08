package com.example.cams.mf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cams.mf.config.JwtUtil;
import com.example.cams.mf.model.UserAuthRequest;
import com.example.cams.mf.model.Users;
import com.example.cams.mf.service.EnrollmentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;

@RestController
@Tag(name="API to generate the JWT Token")
public class AuthController {
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	EnrollmentService enrollmentServiceImpl;
	
	@PostMapping("/auth")
	@PermitAll
	public ResponseEntity<?> getJwtToken(@RequestBody UserAuthRequest userAuthRequest) {
		Users user = enrollmentServiceImpl.getUser(userAuthRequest.username());
		if (user == null)
			return ResponseEntity.status(401).body("Invalid credentials");
		boolean userMatch = BCrypt.checkpw(userAuthRequest.password(), user.getHashedPassword());
		if (!userMatch)
			return ResponseEntity.status(401).body("Invalid credentials");
		String token = jwtUtil.generateToken(userAuthRequest.username(), userAuthRequest.password());
		return ResponseEntity.ok().body(java.util.Map.of("token", token));
	}

}
