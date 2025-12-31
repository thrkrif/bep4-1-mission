package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashLog;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.shared.payout.dto.PayoutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCompletePayoutUseCase {
    private final CashSupport cashSupport;

    public void completePayout(PayoutDto payout){
        Wallet holdingWallet = cashSupport.findHoldingWallet().get(); // 캐시 차감
        Wallet payeeWallet = cashSupport.findWalletByHolderId(payout.getPayeeId()).get(); // System 혹은 Seller가 캐시 수령

        holdingWallet.debit(
                payout.getAmount(),
                payout.isPayeeSystem() ? CashLog.EventType.정산지급__상품판매_수수료 : CashLog.EventType.정산지급__상품판매_대금,
                payout.getModelTypeCode(),
                payout.getId()
        );

        payeeWallet.credit(
                payout.getAmount(),
                payout.isPayeeSystem() ? CashLog.EventType.정산수령__상품판매_수수료 : CashLog.EventType.정산수령__상품판매_대금,
                payout.getModelTypeCode(),
                payout.getId()
        );
    }
}
