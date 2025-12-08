package com.example.cams.mf.service;

import com.example.cams.mf.model.UserLoginRequest;
import com.example.cams.mf.model.Users;

public interface EnrollmentService {
	
	void addUser(UserLoginRequest userRequest);
	
	Users getUser(String userName);

}
