package com.back.global.initData;


import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.app.PostFacade;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.post.app.PostWriteUseCase;
import com.back.boundedContext.post.domain.PostMember;
import com.back.global.rsData.RsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
@Configuration
@Slf4j
/**
 * 빌드 다 되고 초반에 실행이 된다.
 * 초기 데이터를 DB에 주입을 해주는거구나
 * 기존에는 매번 내가 회원가입해서 테스트 계정을 만들었던 경험이 있었음.
 *
 * 프록시를 이용하여 설정을 한거다?
 * 너무 깊게 생각하지는 마라
 */
public class DataInit {
    private final DataInit self;
    private final MemberFacade memberFacade;
    private final PostFacade postFacade;

    public DataInit(@Lazy DataInit self,
                    MemberFacade memberFacade,
                    PostFacade postFacade) {
        this.self = self;
        this.memberFacade = memberFacade;
        this.postFacade = postFacade;
    }

    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            self.makeBaseMembers();
            self.makeBasePosts();
            self.makeBasePostComments();
        };
    }

    @Transactional
    public void makeBasePostComments() {
        Post post1 = postFacade.findById(1).get();
        Post post2 = postFacade.findById(2).get();
        Post post3 = postFacade.findById(3).get();
        Post post4 = postFacade.findById(4).get();
        Post post5 = postFacade.findById(5).get();
        Post post6 = postFacade.findById(6).get();

        PostMember user1Member = postFacade.findPostMemberByUsername("user1").get();
        PostMember user2Member = postFacade.findPostMemberByUsername("user2").get();
        PostMember user3Member = postFacade.findPostMemberByUsername("user3").get();

        if (post1.hasComments()) return;

        post1.addComment(user1Member, "댓글1");
        post1.addComment(user2Member, "댓글2");
        post1.addComment(user3Member, "댓글3");

        post2.addComment(user2Member, "댓글4");
        post2.addComment(user2Member, "댓글5");

        post3.addComment(user3Member, "댓글6");
        post3.addComment(user3Member, "댓글7");

        post4.addComment(user1Member, "댓글8");


    }

    @Transactional
    public void makeBasePosts() {
        if (postFacade.count() > 0) return;

        PostMember user1Member = postFacade.findPostMemberByUsername("user1").get();
        PostMember user2Member = postFacade.findPostMemberByUsername("user2").get();
        PostMember user3Member = postFacade.findPostMemberByUsername("user3").get();

        RsData<Post> post1 = postFacade.write(user1Member, "제목1", "내용1");
        RsData<Post> post2 = postFacade.write(user1Member, "제목2", "내용2");
        RsData<Post> post3 = postFacade.write(user1Member, "제목3", "내용3");
        RsData<Post> post4 = postFacade.write(user2Member, "제목4", "내용4");
        RsData<Post> post5 = postFacade.write(user2Member, "제목5", "내용5");
        RsData<Post> post6 = postFacade.write(user3Member, "제목6", "내용6");
    }

    @Transactional
    public void makeBaseMembers() {
        // 빌드할 때마다 계속 실행되는건데
        // 초기 데이터가 있으면 굳이 하지 않아도 되니까 실행하지마라
        if (memberFacade.count() > 0) return;

        Member systemMember = memberFacade.join("system", "1234", "시스템").getData();
        Member holdingMember = memberFacade.join("holding", "1234", "홀딩").getData();
        Member adminMember = memberFacade.join("admin", "1234", "관리자").getData();
        Member user1Member = memberFacade.join("user1", "1234", "유저1").getData();
        Member user2Member = memberFacade.join("user2", "1234", "유저2").getData();
        Member user3Member = memberFacade.join("user3", "1234", "유저3").getData();
    }
}
