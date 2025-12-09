package com.example.cams.mf.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.cams.mf.config.JwtAuthenticationFilter;
import com.example.cams.mf.service.OrderService;

@WebMvcTest(UserContoller.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderServiceImpl;
    
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void order_callsService_andReturnsSuccess() throws Exception {
        doNothing().when(orderServiceImpl).executeOrder(org.mockito.ArgumentMatchers.any());

        String payload = "{\"fundId\":1,\"quantity\":10}";

        mockMvc.perform(post("/users/order").contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

}
