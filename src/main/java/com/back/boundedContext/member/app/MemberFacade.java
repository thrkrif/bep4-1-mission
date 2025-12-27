package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.domain.MemberPolicy;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.global.exception.DomainException;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 유스케이스(비즈니스 로직) 외부에서 바로 접근 못하도록 하는 수문장
 * in 의 클래스(controller, eventListener ,scheduler)
 * 유즈케이스에 바로 접근할 수 없고, 지금의 Facade를 통해야 한다.
 */
@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberJoinUseCase memberJoinUseCase;
    private final MemberGetRandomSecureTipUseCase memberGetRandomSecureTipUseCase;
    private final MemberSupport memberSupport;

    @Transactional(readOnly = true)
    public long count() {
        return memberSupport.count();
    }

    @Transactional
    public RsData<Member> join(String username, String password, String nickname) {
        return memberJoinUseCase.join(username, password, nickname);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberSupport.findByUsername(username);
    }
    @Transactional(readOnly = true)
    public Optional<Member> findById(int id){
        return memberSupport.findById(id);
    }

    public String getRandomSecureTip(){
        return memberGetRandomSecureTipUseCase.getRandomSecureTip();
    }
}
