package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.ErrorResponse;
import com.eum.haetsal.controller.DTO.response.AccountResponseDTO;
import com.eum.haetsal.controller.DTO.response.TotalTransferHistoryResponseDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import com.eum.haetsal.service.BankService;
import com.eum.haetsal.service.ProfileService;
import com.eum.haetsal.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
@Hidden
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class BankTestController {
    private final BankService bankService;
    private final UserService userService;
    private final UserRepository userRepository;
    @PostMapping("/create/account")
    public String createAccount(@RequestParam Long userId){
        User getUser = userService.findByUserId(userId);
        APIResponse<AccountResponseDTO.Create> accountDTO = bankService.createAccount("1234");
        getUser.setAccountNumber(accountDTO.getData().getAccountNumber());
        getUser.setAccountPassword("1234");
        userRepository.save(getUser);
        return "ok";

    }

    @GetMapping()
    public APIResponse<Long> healthCheck(){
        return bankService.healthCheck();
    }

    @GetMapping("/test")
    public ResponseEntity<APIResponse<AccountResponseDTO.Create>> test(
            @RequestParam String password
    ){

        return ResponseEntity.ok(bankService.createAccount(password));
    }


    // feign client를 이용한 계좌 조회 및 에러 처리 예시
    // 계좌 조회
    @Operation(summary = "계좌 조회", description = "계좌를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "비밀번호 인증 에러 또는 거래상태 비적합",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "402", description = "금액 부족",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "block된 계좌", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/account")
    public ResponseEntity<APIResponse<AccountResponseDTO.AccountInfo>> getAccountInfo(
            @RequestParam
            String accountNumber,
            @RequestParam
            String password
    ) {
        return ResponseEntity.ok(bankService.getAccountInfo(accountNumber, password));
    }

    @Operation(summary = "자유 송금", description = "자유 송금을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "비밀번호 인증 에러 또는 거래상태 비적합",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "402", description = "금액 부족",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "block된 계좌", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/transfer")
    public ResponseEntity<APIResponse<TotalTransferHistoryResponseDTO.GetTotalTransferHistory>> transfer(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam String password,
            @RequestParam Long amount
    ) {
        return ResponseEntity.ok(bankService.transfer(fromAccountNumber, toAccountNumber, password, amount));
    }
}
