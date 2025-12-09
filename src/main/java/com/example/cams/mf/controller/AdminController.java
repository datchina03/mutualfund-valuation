package com.example.cams.mf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cams.mf.model.FundsDetails;
import com.example.cams.mf.model.Users;
import com.example.cams.mf.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@Tag(name="Admin API's",description="API's for admin operations")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/users")
	@Operation(summary="showing a list of Users")
	public ResponseEntity<List<Users>> getUsers(){
		List<Users> usersList=adminService.getAllUsers();
		return ResponseEntity.ok().body(usersList);
	}
	
	
	@DeleteMapping("/delete/{userId}")
	@Operation(summary="Delete the user")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId){
		adminService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/fundnav")
	@Operation(summary="updating the fund Nav")
	public ResponseEntity<String> addFundNav(@Valid @RequestBody FundsDetails fundNavRequest){
		adminService.addFundDetails(fundNavRequest);
		return ResponseEntity.noContent().build();
	}
	
}
