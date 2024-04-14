package com.eum.haetsal.service;

import com.eum.haetsal.controller.DTO.response.ChatResponseDTO;
import com.eum.haetsal.controller.DTO.response.UserResponse;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.marketpost.MarketPostRepository;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.profile.ProfileRepository;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MarketPostRepository marketPostRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    public List<ChatResponseDTO.PostInfo> getPostList(List<String> postIdList){
        List<MarketPost> marketPosts = new ArrayList<>();
        postIdList.forEach(postId -> marketPosts.add(marketPostRepository.findById(Long.valueOf(postId)).orElseThrow(() -> new IllegalArgumentException("invalid postId"))));
        List<ChatResponseDTO.PostInfo> postInfos = marketPosts.stream().map(ChatResponseDTO.PostInfo::new).collect(Collectors.toList());
        return postInfos;
    }

    public List<ChatResponseDTO.UserInfo> getUserList(List<String> userIdList) {
        List<Profile> profiles = new ArrayList<>();
        userIdList.forEach(userId->{
            User getUser = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
            Profile getProfile = profileRepository.findByUser(getUser).orElseThrow(() -> new IllegalArgumentException("프로필 미 생성 유저"));
            profiles.add(getProfile);
        });
        List<ChatResponseDTO.UserInfo> userInfos = profiles.stream().map(ChatResponseDTO.UserInfo::new).collect(Collectors.toList());
        return userInfos;
    }
}
