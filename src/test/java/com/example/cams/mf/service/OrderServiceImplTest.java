package com.example.cams.mf.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cams.mf.model.FundsDetails;
import com.example.cams.mf.model.OrderRequest;
import com.example.cams.mf.model.TransactionDetails;
import com.example.cams.mf.model.UserFundsSummary;
import com.example.cams.mf.repository.AuditRepository;
import com.example.cams.mf.repository.FundsRepository;
import com.example.cams.mf.repository.OrderRepository;
import com.example.cams.mf.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private FundsRepository fundRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void executeOrder_buy_createsAndSavesHoldingsAndAudit() {
        OrderRequest req = new OrderRequest("buy", 1L, 2L, new BigDecimal("5"), new BigDecimal("100.00"));
        FundsDetails fd = new FundsDetails();
        fd.setNav(new BigDecimal("100.00"));

        when(fundRepository.findNavByFundId(2L)).thenReturn(Optional.of(fd));
        when(orderRepository.findByUserIdAndFundId(1L, 2L)).thenReturn(Optional.empty());
        when(orderRepository.save(org.mockito.ArgumentMatchers.any(UserFundsSummary.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(org.mockito.ArgumentMatchers.any(TransactionDetails.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        orderService.executeOrder(req);

        verify(orderRepository).save(org.mockito.ArgumentMatchers.any(UserFundsSummary.class));
        verify(auditRepository).save(org.mockito.ArgumentMatchers.any(TransactionDetails.class));
    }

    @Test
    void executeOrder_navMismatch_doesNotSave() {
        OrderRequest req = new OrderRequest("buy", 1L, 2L, new BigDecimal("5"), new BigDecimal("99.00"));
        FundsDetails fd = new FundsDetails();
        fd.setNav(new BigDecimal("98.00"));

        when(fundRepository.findNavByFundId(2L)).thenReturn(Optional.of(fd));

        orderService.executeOrder(req);

        // should not attempt to save user holdings because nav != userNav
        verify(orderRepository, org.mockito.Mockito.never()).save(org.mockito.ArgumentMatchers.any());
    }

}
