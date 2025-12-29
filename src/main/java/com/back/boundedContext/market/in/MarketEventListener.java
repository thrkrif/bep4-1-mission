package com.back.boundedContext.market.in;

import com.back.boundedContext.market.app.MarketFacade;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MarketEventListener {
    // 알리미
    private final MarketFacade marketFacade;

    // 회원가입이 진행될 때 MarketMember에도 정보를 추가한다
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(MemberJoinedEvent event){
        marketFacade.syncMember(event.getMember());
    }

    // 회원정보 수정 시 MarketMember에도 정보를 추가한다
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(MemberModifiedEvent event){
        marketFacade.syncMember(event.getMember());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(MarketMemberCreatedEvent event) {
        marketFacade.createCart(event.getMember());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(CashOrderPaymentSucceededEvent event){
        marketFacade.handle(event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(CashOrderPaymentFailedEvent event){
        marketFacade.handle(event);
    }
}
