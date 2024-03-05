package com.eum.haetsal.controller.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

public class DealRequestDTO {
    @Schema(description = "거래 생성 요청")
    @Builder
    @Getter
    public static class CreateDeal{
        // 송금자 계좌번호
        @Schema(description = "송금자 계좌번호", example = "123456789012")
        private String accountNumber;
        // 비밀번호
        @Schema(description = "비밀번호", example = "1234")
        private String password;
        // 예치금 ( 지급해야 하는 금액 )
        @Schema(description = "예치금", example = "10000")
        private Long deposit;
        // 최대 인원수
        @Schema(description = "최대 인원수", example = "10")
        private Long maxPeople;
        // 글 id
        @Schema(description = "글 id", example = "1")
        private Long postId;
    }

    @Schema(description = "거래 성사 요청")
    @Getter
    public static class CompleteDeal{
        // 거래ID
        @Schema(description = "거래ID", example = "1")
        @NotEmpty(message = "거래ID를 입력해주세요.")
        private Long dealId;
        // 수신 계좌번호 리스트
        @Schema(description = "수신 계좌번호 리스트")
        @NotEmpty(message = "수신 계좌번호를 입력해주세요.")
        private String[] receiverAccountNumbers;
        // 비밀번호
        @Schema(description = "비밀번호", example = "1234")
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Schema(description = "거래 수정 요청")
    @Getter
    public static class UpdateDeal{
        // 거래ID
        @Schema(description = "거래ID", example = "1")
        @NotEmpty(message = "거래ID를 입력해주세요.")
        private Long dealId;
        // 송금자 계좌번호
        @Schema(description = "송금자 계좌번호", example = "123456789012")
        @NotEmpty(message = "송금자 계좌번호를 입력해주세요.")
        private String senderAccountNumber;
        // 비밀번호
        @Schema(description = "비밀번호", example = "1234")
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
        // 예치금
        @Schema(description = "예치금", example = "10000")
        @NotEmpty(message = "예치금을 입력해주세요.")
        private Long deposit;
        // 최대인원수
        @Schema(description = "최대인원수", example = "10")
        @NotEmpty(message = "최대인원수를 입력해주세요.")
        private Long numberOfPeople;
    }

    @Schema(description = "거래 취소 요청")
    @Getter
    public static class CancelDeal{
        // 거래ID
        @Schema(description = "거래ID", example = "1")
        @NotEmpty(message = "거래ID를 입력해주세요.")
        private Long dealId;
        // 송금자 계좌번호
        @Schema(description = "송금자 계좌번호", example = "123456789012")
        @NotEmpty(message = "송금자 계좌번호를 입력해주세요.")
        private String senderAccountNumber;
        // 비밀번호
        @Schema(description = "비밀번호", example = "1234")
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
    }


    @Getter
    public static class ExecuteDeal{
        // 거래ID
        @Schema(description = "거래ID", example = "1")
        @NotEmpty(message = "거래ID를 입력해주세요.")
        private Long dealId;
        // 송금자 계좌번호
        @Schema(description = "송금자 계좌번호" , example = "123456789012")
        @NotEmpty(message = "송금자 계좌번호를 입력해주세요.")
        private String senderAccountNumber;
        // 비밀번호
        @Schema(description = "비밀번호", example = "1234")
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
    }

}
