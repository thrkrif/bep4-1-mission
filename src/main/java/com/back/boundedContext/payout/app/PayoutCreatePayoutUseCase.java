package com.back.boundedContext.payout.app;

import com.back.boundedContext.payout.out.PayoutMemberRepository;
import com.back.shared.payout.dto.PayoutMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayoutCreatePayoutUseCase {
    public void createPayout(PayoutMemberDto payee) {
        log.debug("createPayout.payee: {}", payee.getId());
    }
}
