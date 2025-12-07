package com.example.cams.mf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cams.mf.model.FundsDetails;
import com.example.cams.mf.model.Users;
import com.example.cams.mf.repository.FundsRepository;
import com.example.cams.mf.repository.UsersRepository;
import com.example.cams.mf.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	FundsRepository fundsRepository;

	@Override
	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}


	@Override
	public void addFundDetails(FundsDetails fundsDetails) {
		fundsRepository.save(fundsDetails);
	}

	@Override
	public void deleteUser(long userId) {
		if (usersRepository.existsById(userId)) {
		    usersRepository.deleteById(userId);
		} else {
		    throw new RuntimeException("User not found");
		}
		
	}

}
