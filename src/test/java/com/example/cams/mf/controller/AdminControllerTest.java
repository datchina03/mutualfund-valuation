package com.example.cams.mf.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.cams.mf.model.FundsDetails;
import com.example.cams.mf.model.Users;
import com.example.cams.mf.service.AdminService;
import com.example.cams.mf.config.JwtAuthenticationFilter;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AdminService adminService;

	@MockitoBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Test
	void getUsers_returnsList() throws Exception {
		Users u1 = new Users(1L, "u1", "h1", "USER");
		Users u2 = new Users(2L, "u2", "h2", "ADMIN");
		when(adminService.getAllUsers()).thenReturn(List.of(u1, u2));

		mockMvc.perform(get("/admin/users")).andExpect(status().isOk());
	}

	@Test
	void deleteUser_callsService_andReturnsOk() throws Exception {
		doNothing().when(adminService).deleteUser(5L);

		mockMvc.perform(delete("/admin/delete/5")).andExpect(status().isNoContent());
	}

	@Test
	void addFundNav_callsService_andReturnsSuccess() throws Exception {
		String payload = "{\"fundName\":\"F1\",\"nav\":123.45}";
		doNothing().when(adminService).addFundDetails(org.mockito.ArgumentMatchers.any(FundsDetails.class));

		mockMvc.perform(post("/admin/fundnav").contentType(MediaType.APPLICATION_JSON).content(payload))
				.andExpect(status().isNoContent());
	}

}
