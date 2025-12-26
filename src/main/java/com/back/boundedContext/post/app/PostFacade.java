package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// 실제 비즈니스 로직
@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostSupport postSupport;
    private final PostWriteUseCase postWriteUseCase;
    private final PostSyncMemberUseCase postSyncMemberUseCase;

    @Transactional(readOnly = true)
    public long count(){
        return postSupport.count();
    }

    @Transactional
    public RsData<Post> write(PostMember author, String title, String content){
        return postWriteUseCase.write(author, title, content);
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(int id){
        return postSupport.findById(id);
    }

    @Transactional
    public PostMember syncMember(MemberDto member){
        PostMember postMember = postSyncMemberUseCase.syncMember(member);
        // 아래 로직 유스 케이스에서 진행
//        PostMember postMember = new PostMember(
//                member.getId(),
//                member.getCreateDate(),
//                member.getModifyDate(),
//                member.getUsername(),
//                "",
//                member.getNickname(),
//                member.getActivityScore()
//        );
//
//        postMember.setId(member.getId());
//        postMember.setCreateDate(member.getCreateDate());
//        postMember.setModifyDate(member.getModifyDate());

        return postSupport.save(postMember);
    }

    @Transactional(readOnly = true)
    public Optional<PostMember> findMemberByUsername(String username){
        return postSupport.findMemberByUsername(username);
    }
}
