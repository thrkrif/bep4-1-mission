package com.back.boundedContext.member.domain;

import com.back.shared.member.domain.SourceMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER_MEMBER")
@NoArgsConstructor
@Getter
public class Member extends SourceMember {
//    @Column(unique = true)
//    private String username;
//    private String password;
//    private String nickname;
//
//    // 활동 점수 : 글 작성 시 3점, 댓글 작성 시 1점
//    private int activityScore;

    public Member(String username, String password, String nickname) {
        super(username, password, nickname);
    }

    public int increaseActivityScore(int amount) {
        setActivityScore(getActivityScore() + amount);
        return getActivityScore();
    }
}