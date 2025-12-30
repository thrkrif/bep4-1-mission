package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {
    private final OrderRepository orderRepository;

    // 유스케이스에서 CashOrderPaymentSucceededEvent 사용 안하도록!

//    public void handle(CashOrderPaymentSucceededEvent event){
//        Order order = orderRepository.findById(event.getOrder().getId()).get();
//
//        order.completePayment();
//    }

    public void completeOrderPayment(int orderId){
        Order order = orderRepository.findById(orderId).get();

        order.completePayment();
    }
}
