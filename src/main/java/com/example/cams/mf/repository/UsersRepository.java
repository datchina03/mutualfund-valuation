package com.example.cams.mf.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.cams.mf.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users,Long>{
	
	List<Users> findAll();
	
	void deleteById(long userId);
	
	Users findByUserName(String userName);
	
	boolean existsById(long userId);
	
}
