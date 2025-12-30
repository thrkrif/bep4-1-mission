package com.back.shared.post.dto;

import com.back.standard.modelType.CanGetModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonCreator.*;

@AllArgsConstructor
@Getter
public class PostDto implements CanGetModelTypeCode {
    private final int id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final int authorId;
    private final String authorName;
    private final String title;
    private final String content;

    @Override
    public String getModelTypeCode() {
        return "Post";
    }
}