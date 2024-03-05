package com.eum.haetsal.controller.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TotalTransferHistoryResponseDTO {

    // 거래 내역 반환
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetTotalTransferHistory {
        private Long id;
        private AccountResponseDTO.AccountInfo senderAccount;
        private AccountResponseDTO.AccountInfo receiverAccount;
        private Long transferAmount;
        private String transferType;

    }
}
