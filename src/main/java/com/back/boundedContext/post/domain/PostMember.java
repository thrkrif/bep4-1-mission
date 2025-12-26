package com.back.boundedContext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_MEMBER")
@NoArgsConstructor
@Getter
/**
 * id, createDate, modifyDate 칼럼에 자동 값 등록 옵션 제거
 * -> BaseIdAndTime 상속받지 말라는건가?
 */
public class PostMember extends BaseIdAndTime {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int activityScore;
}
