package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.boundedContext.cash.out.WalletRepository;
import com.back.shared.cash.dto.CashMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
    private final WalletRepository walletRepository;
    private final CashMemberRepository cashMemberRepository;

    @Transactional
    public Wallet createWallet(CashMemberDto member){
        //JPA가 자동으로 만들어준 거 같은데 getReferenceById 이거 무슨 메서드임?
        CashMember _member = cashMemberRepository.getReferenceById(member.getId());
        Wallet wallet = new Wallet(_member);

        return walletRepository.save(wallet);
    }
}
