package com.example.cams.mf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userId;
	
	@Column(name="username",unique=true,nullable=false)
	private String userName;
	
	@Column(name="password",nullable=false)
	private String hashedPassword;
	
	@Column(name="roleType",nullable=false)
	private String roleType;

}
