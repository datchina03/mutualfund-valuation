package com.example.cams.mf.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.cams.mf.service.impl.JwtUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	JwtUserDetailsService jwtUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String userName=null;
		String jwtToken=null;
		String servletPath=request.getServletPath();
		String requestPath = request.getRequestURI();
		 // Skip JWT validation for public endpoints
        if (isPublicEndpoint(requestPath)||isPublicEndpoint(servletPath)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader=request.getHeader("Authorization");
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			jwtToken=authHeader.substring(7);
			if(jwtUtil.validateToken(jwtToken))
				userName=jwtUtil.extractuserName(jwtToken);
		}
		if(userName!=null &&SecurityContextHolder.getContext().getAuthentication()==null) {
			var userDetails=jwtUserDetailsService.loadUserByUserName(userName);
			UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private boolean isPublicEndpoint(String requestPath) {
		return requestPath.contains("/register") ||
				requestPath.contains("/auth/login") ||
				requestPath.contains("/v3/api-docs") ||
				requestPath.contains("/swagger-ui") ||
				requestPath.contains("/swagger-ui.html");
	}

}
