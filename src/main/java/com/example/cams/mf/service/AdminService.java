package com.example.cams.mf.service;

import java.util.List;

import com.example.cams.mf.model.FundsDetails;
import com.example.cams.mf.model.Users;

public interface AdminService {
	
	List<Users> getAllUsers();

	void addFundDetails(FundsDetails fundsDetails);
	
	void deleteUser(long userId);

}
