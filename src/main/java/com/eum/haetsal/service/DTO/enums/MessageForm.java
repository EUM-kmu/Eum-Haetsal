package com.eum.haetsal.service.DTO.enums;

import lombok.Getter;

@Getter
public enum MessageForm {
    APPLY_NOTIFICATION("타임페이 지원 신청","지원 신청이 왔어요 지금 확인해보세요"),

    ACCEPT_NOTIFICATION("타임페이 지원 수락", "지원 신청이 수락되었습니다 "),

    TRANSFER_NOTIFICATION("타임페이 입출금","원 계좌 잔액")
    ;

    private final String title;

    private final String message;

    MessageForm(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
