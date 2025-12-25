package com.back.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 응답 형식 일관성 맞춰주기
 */
@AllArgsConstructor
@Getter
public class RsData<T> {
    private final String resultCode;
    private final String msg;
    private final T data;

    // 반환할 데이터가 없는 경우를 위함.
    public RsData(String resultCode, String msg){
        this(resultCode, msg, null);
    }
}
