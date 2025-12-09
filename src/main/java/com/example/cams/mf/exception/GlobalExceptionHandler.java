package com.example.cams.mf.exception;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	public Map<String, Object> makeBody(HttpStatus status,String message,String pathUri){
		Map<String,Object> bodyMap=new HashMap<>();
		bodyMap.put("message", message);
		bodyMap.put("error", status.getReasonPhrase());
		bodyMap.put("status",status.value());
		return bodyMap;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex, HttpServletRequest request) {
		Map<String, Object> body = makeBody(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred",
				request.getRequestURI());
		body.put("detail", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Map<String,Object>> handleAuthenticationException(Exception ex,HttpServletRequest re){
		Map<String,Object> body=makeBody(HttpStatus.UNAUTHORIZED,ex.getMessage() != null ? ex.getMessage() : "Authentication failed",re.getRequestURI());
		 return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}
	

}
