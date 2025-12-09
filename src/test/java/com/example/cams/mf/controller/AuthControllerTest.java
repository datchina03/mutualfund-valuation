package com.example.cams.mf.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.cams.mf.config.JwtAuthenticationFilter;
import com.example.cams.mf.config.JwtUtil;
import com.example.cams.mf.model.Users;
import com.example.cams.mf.service.EnrollmentService;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EnrollmentService enrollmentServiceImpl;

	@MockitoBean
	private JwtUtil jwtUtil;

	@MockitoBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	

	@Test
	void login_success_returnsToken() throws Exception {
		String rawPassword = "pass123";
		String hashed = org.springframework.security.crypto.bcrypt.BCrypt.hashpw(rawPassword, org.springframework.security.crypto.bcrypt.BCrypt.gensalt());
		Users user = new Users(1L, "testuser", hashed, "USER");

		when(enrollmentServiceImpl.getUser("testuser")).thenReturn(user);
		when(jwtUtil.generateToken("testuser", "pass123")).thenReturn("fake-token");

		String payload = "{\"username\":\"testuser\",\"password\":\"pass123\"}";

		mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON).content(payload))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value("fake-token"));
	}

}
