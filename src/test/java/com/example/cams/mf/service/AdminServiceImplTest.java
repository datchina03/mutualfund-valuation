package com.example.cams.mf.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cams.mf.model.FundsDetails;
import com.example.cams.mf.model.Users;
import com.example.cams.mf.repository.FundsRepository;
import com.example.cams.mf.repository.UsersRepository;
import com.example.cams.mf.service.impl.AdminServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private FundsRepository fundsRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void getAllUsers_returnsListFromRepository() {
        Users u1 = new Users(1L, "u1", "h1", "USER");
        when(usersRepository.findAll()).thenReturn(List.of(u1));

        var result = adminService.getAllUsers();
        verify(usersRepository).findAll();
    }

    @Test
    void addFundDetails_savesToRepository() {
        FundsDetails fd = new FundsDetails();
        when(fundsRepository.save(fd)).thenReturn(fd);

        adminService.addFundDetails(fd);
        verify(fundsRepository).save(fd);
    }

    @Test
    void deleteUser_existing_deletes() {
        when(usersRepository.existsById(10L)).thenReturn(true);
        doNothing().when(usersRepository).deleteById(10L);

        adminService.deleteUser(10L);
        verify(usersRepository).deleteById(10L);
    }

    @Test
    void deleteUser_missing_throwsRuntime() {
        when(usersRepository.existsById(99L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> adminService.deleteUser(99L));
    }

}
