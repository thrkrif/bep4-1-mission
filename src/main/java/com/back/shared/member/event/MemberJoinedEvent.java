package com.back.shared.member.event;

import com.back.shared.member.dto.MemberDto;
import com.back.standard.event.HaveEventName;

public record MemberJoinedEvent(MemberDto member) implements HaveEventName {
}
