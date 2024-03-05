package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.response.AccountResponseDTO;
import com.eum.haetsal.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class BankTestController {
    private final BankService bankService;

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

    @GetMapping("/account")
    public ResponseEntity<APIResponse<AccountResponseDTO.AccountInfo>> getAccountInfo(
            @RequestParam
            String accountNumber,
            @RequestParam
            String password
    ) throws IllegalAccessException {
        return ResponseEntity.ok(bankService.getAccountInfo(accountNumber, password));
    }
}
