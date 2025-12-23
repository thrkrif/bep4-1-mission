package com.back.boundedContext.member.in;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.app.MemberFacade;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * 이벤트 리스너란?
 * 어떤 작업이 동작하면 그거 확인하고 거기에 맞는 동작 시키는거 같음
 * 이벤트를 이용해서 점수 올리기에 대한 결합도를 낮추었다.
 * 이게 DDD와 어떤 관련이 있을까?
 */

@Component
@RequiredArgsConstructor
public class MemberEventListener {
    private final MemberFacade memberFacade;

    // 게시글을 작성할 때 3점을 추가해줘라
    // 기존 : PostService 클래스의 write
    // AFTER_COMMIT : 실패 시 반영 X
    // (propagation = REQUIRES_NEW)
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PostCreatedEvent event) {
        Member member = memberFacade.findById(event.getPost().getAuthorId()).get();

        member.increaseActivityScore(3);
    }

    // 댓글을 작성할 때 1점을 추가해줘라
    // 기존 : Post 클래스의 addComment
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PostCommentCreatedEvent event) {
        Member member = memberFacade.findById(event.getPostComment().getAuthorId()).get();

        member.increaseActivityScore(1);
    }
}
