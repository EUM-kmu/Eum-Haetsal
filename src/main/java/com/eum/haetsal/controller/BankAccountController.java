package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.request.BankAccountRequestDTO;
import com.eum.haetsal.controller.DTO.response.BankAccountResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("haetsal-service/api/v2/bank-account")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class BankAccountController {
    //게과 정보
    @GetMapping
    public ResponseEntity<APIResponse<BankAccountResponseDTO.AccountInfo>> getAccountInfo( @RequestHeader("userId") String userId){
        return ResponseEntity.ok(APIResponse.<BankAccountResponseDTO.AccountInfo>builder().build());
    }

    //다른 사람 계좌 확인
    @PostMapping("/other")
    public ResponseEntity<APIResponse<BankAccountResponseDTO.AccountInfo>> getOtherAccountInfo(@RequestBody @Validated BankAccountRequestDTO.CheckNickName checkNickName , @RequestHeader("userId") String userId){
        return ResponseEntity.ok(APIResponse.<BankAccountResponseDTO.AccountInfo>builder().build());

    }
//    계좌 이름 변경
    @PutMapping
    public ResponseEntity<APIResponse<BankAccountResponseDTO.AccountInfo>> updateCardname(@RequestBody  BankAccountRequestDTO.CardName cardName,@RequestHeader("userId") String userId){
        return ResponseEntity.ok(APIResponse.<BankAccountResponseDTO.AccountInfo>builder().build());

    }
//    계좌 비밀번호 생성
    @PostMapping("/password")
    public ResponseEntity<APIResponse<BankAccountResponseDTO.AccountInfo>> createPassword(@RequestBody @Validated BankAccountRequestDTO.Password password,@RequestHeader("userId") String userId){
        return ResponseEntity.ok(APIResponse.<BankAccountResponseDTO.AccountInfo>builder().build());

    }
//    비밀 번호 확인
    @PostMapping("/validate")
    public ResponseEntity<APIResponse> validatePassword(@RequestBody @Validated BankAccountRequestDTO.Password password, @RequestHeader("userId") String userId){
        return ResponseEntity.ok(APIResponse.<BankAccountResponseDTO.AccountInfo>builder().build());

    }
//비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<APIResponse> updatePassword(@RequestBody @Validated BankAccountRequestDTO.Password password, @RequestHeader("userId") String userId){
        return ResponseEntity.ok(APIResponse.<BankAccountResponseDTO.AccountInfo>builder().build());

    }
//    송금
    @PostMapping("/remittance")
    public ResponseEntity<APIResponse> remittance(@RequestBody @Validated  BankAccountRequestDTO.Remittance remittance, @RequestHeader("userId") String userId){
        return ResponseEntity.ok(APIResponse.<BankAccountResponseDTO.AccountInfo>builder().build());

    }
//    거래 내역 조회
    @GetMapping("/pay")
    public ResponseEntity<APIResponse<List<BankAccountResponseDTO.HistoryWithInfo>>> getAllHistory(@RequestParam(name = "type",required = false)  @RequestHeader("userId") String userId){
        return ResponseEntity.ok(APIResponse.<List<BankAccountResponseDTO.HistoryWithInfo>>builder().build());

    }


}
