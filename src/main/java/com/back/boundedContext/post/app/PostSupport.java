package com.back.boundedContext.post.app;

import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.boundedContext.post.out.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostSupport {
    private final PostRepository postRepository;
    private final PostMemberRepository postMemberRepository;

    @Transactional(readOnly = true)
    public long count(){
        return postRepository.count();
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(int id){
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PostMember> findMemberByUsername(String username){
        return postMemberRepository.findByUsername(username);
    }

    public PostMember save(PostMember member){
        return postMemberRepository.save(member);
    }
}
