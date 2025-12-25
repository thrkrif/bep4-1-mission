package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.global.rsData.RsData;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase {
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;

    public RsData<Post> write(Member author, String title, String content){
        Post post = postRepository.save(new Post(author, title, content));

        // 점수 보내기 -> 결합도 발생!
//        author.increaseActivityScore(3);

        /**
         * 게시글이 발행했다는 사실만 알려줌!
         */
        eventPublisher.publish(
                new PostCreatedEvent(
                        new PostDto(post)
                )
        );

        return new RsData<>("201-1", "%d번 글이 생성되었습니다.".formatted(post.getId(), post));
        /**
         *  postRepository.save(post); 시
         *  DB에 INSERT
         *  ID 생성
         *  createdAt, updateAt 등 채워짐
         *  엔티티가 영속 상태(Persistnet)로 전환됨.
         */
    }
}
