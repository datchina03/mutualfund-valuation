package com.example.cams.mf.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.cams.mf.model.Users;
import com.example.cams.mf.repository.UsersRepository;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsServiceTest {

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    JwtUserDetailsService service;

    @Test
    void loadUserByUserName_success() {
        Users u = new Users(1L, "joe", "hashed", "ROLE_USER");
        when(usersRepository.findByUserName("joe")).thenReturn(u);

        UserDetails ud = service.loadUserByUserName("joe");

        assertEquals("joe", ud.getUsername());
        assertEquals("hashed", ud.getPassword());
    }

    @Test
    void loadUserByUserName_notFound() {
        when(usersRepository.findByUserName("nobody")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUserName("nobody"));
    }

}
