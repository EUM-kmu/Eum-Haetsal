package com.eum.haetsal.service;

import com.eum.haetsal.client.AuthClient;
import com.eum.haetsal.client.BankClient;
import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.response.AccountResponseDTO;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.marketpost.MarketPostRepository;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.profile.ProfileRepository;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.eum.haetsal.domain.marketpost.Status.RECRUITING;
import static com.eum.haetsal.domain.marketpost.Status.RECRUITMENT_COMPLETED;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BankService bankService;
    private final ProfileRepository profileRepository;
    private final AuthClient authClient;
    private final ProfileService profileService;
    private final MarketPostService marketPostService;
    private final MarketPostRepository marketPostRepository;
    private final BankClient bankClient;

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
            deletedUser.setAccountPassword("");
            userRepository.save(user);
            userRepository.save(deletedUser);
        }
    }
    @Transactional
    public void withdrawal(String authorizationHeader,String userId){
        authClient.withdrawal(authorizationHeader,userId);

        User user = findByUserId(Long.valueOf(userId));
        Profile profile = profileService.findByUser(Long.valueOf(userId));

        // 진행중인 거래가 있다면 거래 취소
        // 모집중, 모집완료 모두 게시글과 거래 취소 및 삭제
        List<MarketPost> mps = marketPostRepository.findByProfileAndDeletedAndStatusIn(profile, false,List.of(RECRUITING, RECRUITMENT_COMPLETED));
        for (MarketPost mp : mps) {
            marketPostService.delete(mp.getMarketPostId(), profile);
        }

        // 프로필 이름 변경 및 알수없는 계정으로 처리
        profileService.removePrivacy(profile);

        // 뱅크 계좌 블락
        bankClient.blockAccount(user.getAccountNumber());
    }

}
