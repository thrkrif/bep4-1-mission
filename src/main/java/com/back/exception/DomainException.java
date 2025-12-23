package com.back.exception;

import lombok.Getter;

@Getter
public class DomainException extends  RuntimeException{
    private final String resultCode;
    private final String msg;

    public DomainException(String resultCode, String msg) {
        // 부모 클래스(RuntimeException) 생성자를 호출
        // super는 생성자의 첫 줄이어야 한다.
        super(resultCode + " : " + msg);
        this.resultCode = resultCode;
        this.msg = msg;
    }

}
