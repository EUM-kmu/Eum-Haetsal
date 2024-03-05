package com.eum.haetsal.service;

import com.eum.haetsal.client.BankClient;
import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.request.AccountRequestDTO;
import com.eum.haetsal.controller.DTO.response.AccountResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public APIResponse<AccountResponseDTO.AccountInfo> getAccountInfo(String accountNumber, String password) {
        return bankClient.getAccountInfo(accountNumber, password).getBody();
    }
}
