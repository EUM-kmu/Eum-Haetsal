package com.eum.haetsal.service;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.response.AccountResponseDTO;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BankService bankService;

    @Transactional
    public String create(Long userId, String password){
        User user = User.toEntity(userId);
        APIResponse<AccountResponseDTO.Create> accountDTO = bankService.createAccount(password);
        user.setAccountNumber(accountDTO.getData().getAccountNumber());
        userRepository.save(user);
        return accountDTO.getData().getAccountNumber();
    }
}
