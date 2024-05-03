package com.eum.haetsal.service;

import com.eum.haetsal.client.AuthClient;
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
    private final AuthClient authClient;

    public User findByUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));
    }

    @Transactional
    public void create(Long userId, String password,Long previousUserId){
        if(userRepository.existsById(userId)){
           User user = userRepository.findById(userId).get();
           if (profileRepository.existsByUser(user)) throw new IllegalArgumentException("이미 프로필이 있는 회원");

        }
        else {
            if(previousUserId == -1 || previousUserId ==null){
                User user = User.toEntity(userId, password);
                APIResponse<AccountResponseDTO.Create> accountDTO = bankService.createAccount(password);
                user.setAccountNumber(accountDTO.getData().getAccountNumber());
                userRepository.save(user);
                return;
            }
            User deletedUser = userRepository.findById(previousUserId).orElseThrow(() -> new IllegalArgumentException("탈퇴한 유저id 가 잘못되었습니다"));
            User user = User.toEntity(userId, deletedUser.getAccountPassword());
            user.setAccountNumber(deletedUser.getAccountNumber());
            deletedUser.setAccountNumber("");
            deletedUser.setAccountNumber("");
            userRepository.save(user);
            userRepository.save(deletedUser);
        }
    }
    @Transactional
    public void withdrawal(String authorizationHeader,String userId){
        authClient.withdrawal(authorizationHeader,userId);
    }

}
