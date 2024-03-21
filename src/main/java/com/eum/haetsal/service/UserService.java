package com.eum.haetsal.service;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.response.AccountResponseDTO;
import com.eum.haetsal.domain.profile.ProfileRepository;
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
    private final ProfileRepository profileRepository;

    public User findByUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));
    }

    @Transactional
    public String create(Long userId, String password){
        if(userRepository.existsById(userId)){
           User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));
           if (profileRepository.existsByUser(user)) throw new IllegalArgumentException("이미 프로필이 있는 회원");
              return user.getAccountNumber();
        }
        else {
            User user = User.toEntity(userId, password);
            APIResponse<AccountResponseDTO.Create> accountDTO = bankService.createAccount(password);
            user.setAccountNumber(accountDTO.getData().getAccountNumber());
            userRepository.save(user);
            return accountDTO.getData().getAccountNumber();
        }
    }
}
