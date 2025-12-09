package com.example.cams.mf.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.cams.mf.model.UserFundsSummary;

@DataJpaTest
public class OrderRepositoryJpaTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void saveAndFindByUserAndFund() {
        UserFundsSummary u = new UserFundsSummary();
        u.setUserId(1L);
        u.setFundId(2L);
        u.setUnitsOwned(new BigDecimal("10"));
        u.setTotalUnitsValue(new BigDecimal("1000"));
        orderRepository.save(u);

        Optional<UserFundsSummary> found = orderRepository.findByUserIdAndFundId(1L, 2L);
        assertThat(found).isPresent();
        assertThat(found.get().getUnitsOwned()).isEqualByComparingTo(new BigDecimal("10"));
    }

}
