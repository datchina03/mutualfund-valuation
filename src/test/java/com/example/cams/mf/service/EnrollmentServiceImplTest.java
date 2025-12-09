package com.example.cams.mf.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cams.mf.model.UserLoginRequest;
import com.example.cams.mf.model.Users;
import com.example.cams.mf.repository.UsersRepository;
import com.example.cams.mf.service.impl.EnrollmentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void addUser_savesUserWithHashedPassword() {
        UserLoginRequest req = new UserLoginRequest("bob", "secret", "USER");

        enrollmentService.addUser(req);

        // verify save called - we cannot assert hash exact value, but ensure user saved with username
        verify(usersRepository).save(org.mockito.ArgumentMatchers.argThat((Users u) -> u.getUserName().equals("bob") && u.getHashedPassword() != null && !u.getHashedPassword().isEmpty()));
    }

}
