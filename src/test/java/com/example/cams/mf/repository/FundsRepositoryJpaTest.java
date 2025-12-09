package com.example.cams.mf.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.cams.mf.model.FundsDetails;

@DataJpaTest
public class FundsRepositoryJpaTest {

    @Autowired
    private FundsRepository fundsRepository;

    @Test
    void saveAndFindNavByFundId() {
        FundsDetails f = new FundsDetails();
        f.setFundId(100L);
        f.setNav(new BigDecimal("12.34"));
        fundsRepository.save(f);

        Optional<FundsDetails> found = fundsRepository.findNavByFundId(100L);
        assertThat(found).isPresent();
        assertThat(found.get().getNav()).isEqualByComparingTo(new BigDecimal("12.34"));
    }

}
