package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.global.exception.DomainException;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 비즈니스 로직을 구현하는 영역
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberJoinUseCase {
    private final MemberRepository memberRepository;
    private final EventPublisher eventPublisher;

    // 회원가입
    public RsData<Member> join(String username, String password, String nickname) {
        memberRepository.findByUsername(username).ifPresent(
                m -> {
                    throw new DomainException("409-1", "이미 존재하는 username 입니다.");
                }
        );
        Member member = memberRepository.save(new Member(username, password, nickname));

        /**
         * 굳이 왜 MemberJoinedEvent로 DTO를 감쌈?
         * 🌟 어떤 '사건'인지 알려주기 위함
         * 🌟 이벤트 리스너가 의미를 추측해야 함
         * 🌟 나중에 이벤트 종류 늘어나면 지옥
         */
        eventPublisher.publish(new MemberJoinedEvent(member.toDto()));
        log.info("MemberJoinedEvent 발행됨: {}", member.getUsername());

        return new RsData<>("201-1", "%d번 회원이 생성되었습니다.".formatted(member.getId()), member);
    }
}
