package com.eum.haetsal.service;

import com.eum.haetsal.client.BankClient;
import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.AccountRequestDTO;
import com.eum.haetsal.controller.DTO.request.DealRequestDTO;
import com.eum.haetsal.controller.DTO.response.AccountResponseDTO;
import com.eum.haetsal.controller.DTO.response.DealResponseDTO;
import com.eum.haetsal.controller.DTO.response.TotalTransferHistoryResponseDTO;
import com.eum.haetsal.exception.BlockAccountException;
import com.eum.haetsal.exception.FeignClientException;
import com.eum.haetsal.exception.InsufficientAmountException;
import com.eum.haetsal.exception.WrongPasswordException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankService {
    private final BankClient bankClient;

    public APIResponse<AccountResponseDTO.Create> createAccount(String password){
        APIResponse<AccountResponseDTO.Create> body = bankClient.createAccount(AccountRequestDTO.CreateAccount.builder().password(password).build()).getBody();
        log.info("createAccount response: {}", body);
        return body;
    }

    public APIResponse<Long> healthCheck(){
        return bankClient.healthCheck().getBody();
    }

    // 계좌 조회
//    @ApiResponse(responseCode = "200", description = "성공"),
//    @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//    @ApiResponse(responseCode = "401", description = "비밀번호 인증 에러 또는 거래상태 비적합",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//    @ApiResponse(responseCode = "402", description = "금액 부족",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//    @ApiResponse(responseCode = "403", description = "block된 계좌", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//    @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public APIResponse<AccountResponseDTO.AccountInfo> getAccountInfo(String accountNumber, String password) {
        try {
            APIResponse<AccountResponseDTO.AccountInfo> response = bankClient.getAccountInfo(accountNumber, password).getBody();
            return response;
        } catch (FeignClientException e) {
            switch (e.getStatus()){
                case 400:
                    throw new IllegalArgumentException(e.getErrorForm().getReason());
                case 401:
                    throw new WrongPasswordException(e.getErrorForm().getReason());
                case 402:
                    throw new InsufficientAmountException(e.getErrorForm().getReason());
                case 403:
                    throw new BlockAccountException(e.getErrorForm().getReason());
                default:
                    throw new RuntimeException(e.getErrorForm().getReason());
            }
        }

    }

    public APIResponse<TotalTransferHistoryResponseDTO.GetTotalTransferHistory> transfer(String fromAccountNumber, String toAccountNumber, String password,Long amount) {
        try {
            AccountRequestDTO.Transfer transfer = AccountRequestDTO.Transfer.builder()
                    .accountNumber(fromAccountNumber)
                    .receiverAccountNumber(toAccountNumber)
                    .password(password)
                    .amount(amount)
                    .build();
            APIResponse<TotalTransferHistoryResponseDTO.GetTotalTransferHistory> response = bankClient.transfer(transfer).getBody();
            return response;
        } catch (FeignClientException e) {
            switch (e.getStatus()){
                case 400:
                    throw new BadRequestException(e.getErrorForm().getReason());
                case 401:
                    throw new WrongPasswordException(e.getErrorForm().getReason());
                case 402:
                    throw new InsufficientAmountException(e.getErrorForm().getReason());
                case 403:
                    throw new BlockAccountException(e.getErrorForm().getReason());
                default:
                    throw new RuntimeException(e.getErrorForm().getReason());
            }
        }
    }

    public APIResponse<DealResponseDTO.createDeal> createDeal(String accountNumber, String password, Long amount, Long maxPeople, Long postId){
        try{
            DealRequestDTO.CreateDeal createDeal = DealRequestDTO.CreateDeal.builder()
                    .accountNumber(accountNumber)
                    .password(password)
                    .deposit(amount)
                    .maxPeople(maxPeople)
                    .postId(postId)
                    .build();
            APIResponse<DealResponseDTO.createDeal> response = bankClient.createDeal(createDeal).getBody();
            return response;
        } catch (FeignClientException e) {
            switch (e.getStatus()){
                case 400:
                    throw new BadRequestException(e.getErrorForm().getReason());
                case 401:
                    throw new WrongPasswordException(e.getErrorForm().getReason());
                case 402:
                    throw new InsufficientAmountException(e.getErrorForm().getReason());
                case 403:
                    throw new BlockAccountException(e.getErrorForm().getReason());
                default:
                    throw new RuntimeException(e.getErrorForm().getReason());
            }
        }
    }

    public APIResponse<DealResponseDTO.createDeal> successDeal(Long dealId, String[] receiverAccountNumbers, String password){
        try{
            DealRequestDTO.CompleteDeal completeDeal = DealRequestDTO.CompleteDeal.builder()
                    .dealId(dealId)
                    .receiverAccountNumbers(receiverAccountNumbers)
                    .password(password)
                    .build();

            return bankClient.success(completeDeal).getBody();
        } catch (FeignClientException e) {
            switch (e.getStatus()){
                case 400:
                    throw new BadRequestException(e.getErrorForm().getReason());
                case 401:
                    throw new WrongPasswordException(e.getErrorForm().getReason());
                case 402:
                    throw new InsufficientAmountException(e.getErrorForm().getReason());
                case 403:
                    throw new BlockAccountException(e.getErrorForm().getReason());
                default:
                    throw new RuntimeException(e.getErrorForm().getReason());
            }
        }
    }

    public APIResponse<DealResponseDTO.createDeal> updateDeal(Long dealId, String senderAccountNumber, String password, Long deposit, Long maxPeople){
        try{
            DealRequestDTO.UpdateDeal updateDeal = DealRequestDTO.UpdateDeal.builder()
                    .dealId(dealId)
                    .senderAccountNumber(senderAccountNumber)
                    .password(password)
                    .deposit(deposit)
                    .numberOfPeople(maxPeople)
                    .build();
            return bankClient.updateDeal(updateDeal).getBody();
        } catch (FeignClientException e) {
            switch (e.getStatus()){
                case 400:
                    throw new BadRequestException(e.getErrorForm().getReason());
                case 401:
                    throw new WrongPasswordException(e.getErrorForm().getReason());
                case 402:
                    throw new InsufficientAmountException(e.getErrorForm().getReason());
                case 403:
                    throw new BlockAccountException(e.getErrorForm().getReason());
                default:
                    throw new RuntimeException(e.getErrorForm().getReason());
            }
        }
    }

    public APIResponse<DealResponseDTO.createDeal> cancelDeal(Long dealId, String accountNumber, String password){
        try{
            DealRequestDTO.CancelDeal cancelDeal = DealRequestDTO.CancelDeal.builder()
                    .dealId(dealId)
                    .senderAccountNumber(accountNumber)
                    .password(password)
                    .build();
            return bankClient.cancelDeal(cancelDeal).getBody();
        } catch (FeignClientException e) {
            switch (e.getStatus()){
                case 400:
                    throw new BadRequestException(e.getErrorForm().getReason());
                case 401:
                    throw new WrongPasswordException(e.getErrorForm().getReason());
                case 402:
                    throw new InsufficientAmountException(e.getErrorForm().getReason());
                case 403:
                    throw new BlockAccountException(e.getErrorForm().getReason());
                default:
                    throw new RuntimeException(e.getErrorForm().getReason());
            }
        }
    }

    public void executeDeal(Long dealId, String senderAccountNumber, String password, Long totalAmount, List<DealRequestDTO.ReceiverAndAmount> receiverAndAmounts){
        try{
            DealRequestDTO.ExecuteDeal executeDeal = DealRequestDTO.ExecuteDeal.builder()
                    .dealId(dealId)
                    .senderAccountNumber(senderAccountNumber)
                    .totalAmount(totalAmount)
                    .receiverAndAmounts(receiverAndAmounts)
                    .password(password)
                    .build();
            bankClient.executeDeal(executeDeal);
        } catch (FeignClientException e) {
            switch (e.getStatus()){
                case 400:
                    throw new BadRequestException(e.getErrorForm().getReason());
                case 401:
                    throw new WrongPasswordException(e.getErrorForm().getReason());
                case 402:
                    throw new InsufficientAmountException(e.getErrorForm().getReason());
                case 403:
                    throw new BlockAccountException(e.getErrorForm().getReason());
                default:
                    throw new RuntimeException(e.getErrorForm().getReason());
            }
        }
    }

    public void rollbackDeal(Long dealId, String accountNumber, String accountPassword) {
        try{
            DealRequestDTO.RollbackDeal rollbackDeal = DealRequestDTO.RollbackDeal.builder()
                    .dealId(dealId)
                    .senderAccountNumber(accountNumber)
                    .password(accountPassword)
                    .build();
            bankClient.rollbackDeal(rollbackDeal);
        } catch (FeignClientException e) {
            switch (e.getStatus()){
                case 400:
                    throw new BadRequestException(e.getErrorForm().getReason());
                case 401:
                    throw new WrongPasswordException(e.getErrorForm().getReason());
                case 402:
                    throw new InsufficientAmountException(e.getErrorForm().getReason());
                case 403:
                    throw new BlockAccountException(e.getErrorForm().getReason());
                default:
                    throw new RuntimeException(e.getErrorForm().getReason());
            }
        }
    }
}
