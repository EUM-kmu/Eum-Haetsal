package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.profile.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ChatResponseDTO {
    @Getter
    @Setter
    @Builder
    public static class ChatInfo{
        private MarketPostResponseDTO.MarketPostResponse marketPost;

        private ProfileResponseDTO.UserInfo userInfo;
        public ChatInfo(MarketPostResponseDTO.MarketPostResponse marketPost, Profile profile) {
            this.marketPost = marketPost;
            this.userInfo = ProfileResponseDTO.toUserInfo(profile);
        }

    }

}
