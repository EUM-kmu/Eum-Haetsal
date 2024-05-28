package com.eum.haetsal.service;

import com.eum.haetsal.controller.DTO.response.ChatResponseDTO;
import com.eum.haetsal.controller.DTO.response.TimeBankResponseDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.profile.ProfileRepository;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeBankService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public List<TimeBankResponseDTO.UserInfo> getUserList(List<String> accountNumberList) {
        List<Profile> profiles = new ArrayList<>();
        accountNumberList.forEach(accountNumber->{
            User getUser = userRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new IllegalArgumentException("Invalid Account Number"));
            Profile getProfile = profileRepository.findByUser(getUser).orElseThrow(() -> new IllegalArgumentException("프로필 미 생성 유저"));
            profiles.add(getProfile);
        });

        List<TimeBankResponseDTO.UserInfo> userInfos = profiles.stream().map(TimeBankResponseDTO.UserInfo::new).collect(Collectors.toList());
        return userInfos;
    }
}
