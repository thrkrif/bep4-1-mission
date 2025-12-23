package com.back.initData;


import com.back.entity.Member;
import com.back.entity.Post;
import com.back.service.MemberService;
import com.back.service.PostService;
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
    private final MemberService memberService;
    private final PostService postService;

    public DataInit(@Lazy DataInit self,
                    MemberService memberService,
                    PostService postService) {
        this.self = self;
        this.memberService = memberService;
        this.postService = postService;
    }

    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            self.makeBaseMembers();
            self.makeBasePosts();
        };
    }

    @Transactional
    public void makeBasePosts() {
        if (postService.count() > 0) return;

        Member user1Member = memberService.findByUsername("user1").get();
        Member user2Member = memberService.findByUsername("user2").get();
        Member user3Member = memberService.findByUsername("user3").get();

        Post post1 = postService.write(user1Member, "제목1", "내용1");
        Post post2 = postService.write(user1Member, "제목2", "내용2");
        Post post3 = postService.write(user1Member, "제목3", "내용3");
        Post post4 = postService.write(user2Member, "제목4", "내용4");
        Post post5 = postService.write(user2Member, "제목5", "내용5");
        Post post6 = postService.write(user3Member, "제목6", "내용6");
    }

    @Transactional
    public void makeBaseMembers() {
        // 빌드할 때마다 계속 실행되는건데
        // 초기 데이터가 있으면 굳이 하지 않아도 되니까 실행하지마라
        if (memberService.count() > 0) return;

        Member systemMember = memberService.join("system", "1234", "시스템");
        Member holdingMember = memberService.join("holding", "1234", "홀딩");
        Member adminMember = memberService.join("admin", "1234", "관리자");
        Member user1Member = memberService.join("user1", "1234", "유저1");
        Member user2Member = memberService.join("user2", "1234", "유저2");
        Member user3Member = memberService.join("user3", "1234", "유저3");
    }
}
