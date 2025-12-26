package com.back.global.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseIdAndTimeManual extends BaseEntity {
    @Id
    private int id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

}
