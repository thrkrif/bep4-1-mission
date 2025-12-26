package com.back.shared.cash.event;

import com.back.shared.cash.dto.CashMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이벤트는 어떤 이벤트가 발생했는지 알림 역할이고
 * Dto를 담는다
 */
@Getter
@AllArgsConstructor
public class CashMemberCreatedEvent {
    private final CashMemberDto member;
}
