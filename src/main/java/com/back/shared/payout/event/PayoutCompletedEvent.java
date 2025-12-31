package com.back.shared.payout.event;

import com.back.shared.payout.dto.PayoutDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PayoutCompletedEvent {
    private final PayoutDto payout;
}
