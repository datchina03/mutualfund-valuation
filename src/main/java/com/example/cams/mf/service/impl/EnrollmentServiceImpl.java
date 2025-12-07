package com.example.cams.mf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.cams.mf.model.UserLoginRequest;
import com.example.cams.mf.model.Users;
import com.example.cams.mf.repository.UsersRepository;
import com.example.cams.mf.service.EnrollmentService;

@Service
public class EnrollmentServiceImpl implements EnrollmentService{
	
	@Autowired
	UsersRepository usersRepository;

	@Override
	public void addUser(UserLoginRequest userRequest) {
		String hashedPassword=BCrypt.hashpw(userRequest.password(), BCrypt.gensalt());
		Users users=new Users();
		users.setHashedPassword(hashedPassword);
		users.setUserName(userRequest.username());
		users.setRoleType(userRequest.RoleType());
		usersRepository.save(users);
	}

}
