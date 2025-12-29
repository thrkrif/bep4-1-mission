package com.back.boundedContext.cash.in;

import com.back.boundedContext.cash.app.CashFacade;
import com.back.shared.cash.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cash/wallets")
public class ApiV1WalletController {
    // 컨트롤러에서는 파사드 사용
    // 파사드에서는 유스 케이스 사용, 서포트 사용
    // 유스케이스는 비즈니스 로직
    // 서포트에서는 레포지토리 find, count emd
    private final CashFacade cashFacade;

    @GetMapping("/by-holder/{holderId}")
    @Transactional(readOnly = true)
    public WalletDto getItemByHolder(
            @PathVariable int holderId
    ) {
        return cashFacade
                .findWalletByHolderId(holderId)
                .map(WalletDto::new)
                .get();
    }

}
