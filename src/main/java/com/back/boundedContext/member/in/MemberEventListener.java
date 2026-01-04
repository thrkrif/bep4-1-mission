package com.back.boundedContext.member.in;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.app.MemberFacade;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class MemberEventListener {
    private final MemberFacade memberFacade;

    /**
     * Spring Event
     */
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

    /**
     * Kafka
     * 현 상태 - AFTER_COMMIT 보장이 안됨
     * 해결 방안 1. Kafka Transactional Producer 사용
     * 해결 방안 2. Outbox 패턴 사용
     */
//    @KafkaListener(topics = "PostCreatedEvent", groupId = "MemberEventListener__handlePostCreated")
//    @Transactional(propagation = REQUIRES_NEW)
//    public void handle(PostCreatedEvent event) {
//        Member member = memberFacade.findById(event.getPost().getAuthorId()).get();
//        member.increaseActivityScore(3);
//    }
//
//    @KafkaListener(topics = "PostCommentCreatedEvent", groupId = "MemberEventListener__handlePostCommentCreated")
//    @Transactional(propagation = REQUIRES_NEW)
//    public void handle(PostCommentCreatedEvent event) {
//        Member member = memberFacade.findById(event.getPostComment().getAuthorId()).get();
//        member.increaseActivityScore(1);
//    }

}
