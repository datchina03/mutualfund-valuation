package com.example.cams.mf.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cams.mf.model.Users;
import com.example.cams.mf.repository.UsersRepository;

@Service
public class JwtUserDetailsService {
	
	@Autowired
	UsersRepository usersRepository;

	public UserDetails loadUserByUserName(String username) {
		Users users=usersRepository.findByUserName(username);
		  if (users == null) 
	            throw new UsernameNotFoundException("User not found: " + username);
		GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(users.getRoleType());
		return new User(users.getUserName(),users.getHashedPassword(),Collections.singleton(grantedAuthority));
	}
	

}
