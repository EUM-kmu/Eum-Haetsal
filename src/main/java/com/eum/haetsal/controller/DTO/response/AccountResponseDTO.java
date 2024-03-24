package com.eum.haetsal.controller.DTO.response;


import com.eum.haetsal.controller.DTO.request.AccountRequestDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.eum.haetsal.common.Constant.BATCH_TYPE;
import static com.eum.haetsal.common.Constant.FREE_TYPE;


public class AccountResponseDTO {
    // 계좌 생성 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        private String accountNumber;
    }

    // 계좌 조회 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountInfo {
        private String accountNumber;
        private Long totalBudget;
        private Long availableBudget;
        private Boolean isBlocked;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transfer {
        private String senderAccountNumber;
        private String receiverAccountNumber;
        private Long amount;
        private String password;
        private String transferType;

        public static Transfer freeTransfer(AccountRequestDTO.Transfer requestTransfer){
            return Transfer.builder()
                    .senderAccountNumber(requestTransfer.getAccountNumber())
                    .receiverAccountNumber(requestTransfer.getReceiverAccountNumber())
                    .amount(requestTransfer.getAmount())
                    .password(requestTransfer.getPassword())
                    .transferType(FREE_TYPE)
                    .build();
        }

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Block {
        private String accountNumber;
    }
}
