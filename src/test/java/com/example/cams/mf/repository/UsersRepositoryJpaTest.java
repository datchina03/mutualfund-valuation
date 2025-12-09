package com.example.cams.mf.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.cams.mf.model.Users;

@DataJpaTest
public class UsersRepositoryJpaTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void saveAndFindAllWorks() {
        Users u = new Users();
        u.setUserName("alice");
        u.setHashedPassword("hp");
        u.setRoleType("USER");
        usersRepository.save(u);

        List<Users> all = usersRepository.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all.get(0).getUserName()).isEqualTo("alice");
    }

}
