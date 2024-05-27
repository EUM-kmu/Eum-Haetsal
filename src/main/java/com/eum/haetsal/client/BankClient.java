package com.eum.haetsal.client;

import com.eum.haetsal.EumHaetsalApplication;
import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.request.AccountRequestDTO;
import com.eum.haetsal.controller.DTO.request.DealRequestDTO;
import com.eum.haetsal.controller.DTO.response.AccountResponseDTO;
import com.eum.haetsal.controller.DTO.response.DealResponseDTO;
import com.eum.haetsal.controller.DTO.response.TotalTransferHistoryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "bank-service", url = "http://bank:8080")
public interface BankClient {

    // AccountController
    /**
     * health check
     * @return
     */
    @GetMapping("/")
    ResponseEntity<APIResponse<Long>> healthCheck();

    /**
     * 계좌 생성
     * @param createAccount
     * @return ResponseEntity<APIResponse<AccountResponseDTO.Create>>
     */
    @PostMapping(value = "/account")
    ResponseEntity<APIResponse<AccountResponseDTO.Create>> createAccount(@RequestBody AccountRequestDTO.CreateAccount createAccount);


    /**
     * 계좌 조회
     * @param accountNumber
     * @param password
     * @return ResponseEntity<APIResponse<AccountResponseDTO.AccountInfo>>
     */
    @GetMapping("/account")
    ResponseEntity<APIResponse<AccountResponseDTO.AccountInfo>> getAccountInfo(@RequestParam String accountNumber, @RequestParam String password);

    /**
     * 송금
     * @param deposit
     * @return ResponseEntity<APIResponse<TotalTransferHistoryResponseDTO.GetTotalTransferHistory>>
     */
    @PostMapping("/account/transfer")
    ResponseEntity<APIResponse<TotalTransferHistoryResponseDTO.GetTotalTransferHistory>> transfer(@RequestBody AccountRequestDTO.Transfer transfer);

    /**
     * 계좌 block
     * @param accountNumber
     * @return ResponseEntity<APIResponse<AccountResponseDTO.Block>>
     */
    @PatchMapping("/account/block")
    ResponseEntity<APIResponse<AccountResponseDTO.Block>> blockAccount(@RequestParam String accountNumber);

    // DealController
    /**
     * 거래 생성
     * @param createDeal
     * @return ResponseEntity<APIResponse<DealResponseDTO.CreateDeal>>
     */
    @PostMapping("/deal")
    ResponseEntity<APIResponse<DealResponseDTO.createDeal>> createDeal(@RequestBody DealRequestDTO.CreateDeal createDeal);

    /**
     * 거래 성사
     * @param completeDeal
     * @return ResponseEntity<APIResponse<DealResponseDTO.createDeal>>
     */
    @PostMapping("/deal/success")
    ResponseEntity<APIResponse<DealResponseDTO.createDeal>> success(@RequestBody DealRequestDTO.CompleteDeal completeDeal);

    /**
     * 거래 수정
     * @param updateDeal
     * @return ResponseEntity<APIResponse<DealResponseDTO.createDeal>>
     */
    @PatchMapping("/deal")
    ResponseEntity<APIResponse<DealResponseDTO.createDeal>> updateDeal(@RequestBody DealRequestDTO.UpdateDeal updateDeal);

    /**
     * 거래 취소
     * @param cancelDeal
     * @return ResponseEntity<APIResponse<DealResponseDTO.createDeal>>
     */
    @DeleteMapping("/deal")
    ResponseEntity<APIResponse<DealResponseDTO.createDeal>> cancelDeal(@RequestBody DealRequestDTO.CancelDeal cancelDeal);

    /**
     * 거래 수행
     * @param executeDeal
     * @return ResponseEntity<APIResponse<List<TotalTransferHistoryResponseDTO.GetTotalTransferHistory>>>
     */
    @PostMapping("/deal/execute")
    ResponseEntity<APIResponse> executeDeal(@RequestBody DealRequestDTO.ExecuteDeal executeDeal);

    /**
     * 거래 롤백
     * @param rollback
     * @return ResponseEntity<APIResponse>
     */
    @PatchMapping("/deal/rollback")
    ResponseEntity<APIResponse> rollbackDeal(@RequestBody DealRequestDTO.RollbackDeal rollback);

}
