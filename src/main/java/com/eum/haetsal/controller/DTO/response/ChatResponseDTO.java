package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.marketpost.Status;
import com.eum.haetsal.domain.profile.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ChatResponseDTO {
    @Getter
    @Setter
    public static class ChatInfo{
        private MarketPostResponseDTO.MarketPostResponse marketPost;

        private ProfileResponseDTO.UserInfo userInfo;
        public ChatInfo(MarketPostResponseDTO.MarketPostResponse marketPost, Profile profile) {
            this.marketPost = marketPost;
            this.userInfo = ProfileResponseDTO.toUserInfo(profile);
        }
    }
    @Getter
    @Setter
    public static class PostInfo{
        private Long postId;
        private Status status;
        private String title;

        public PostInfo(MarketPost marketPost) {
            this.postId = marketPost.getMarketPostId();
            this.status = marketPost.getStatus();
            this.title = marketPost.getTitle();
        }
    }
    @Getter
    @Setter
    public static class UserInfo{
        private Long userId;
        private Long profileId;
        private String profileImage;
        private String nickName;

        public UserInfo(Profile profile) {
            this.userId = profile.getUser().getUserId();
            this.profileId = profile.getProfileId();
            this.profileImage = profile.getProfileImage();
            this.nickName = profile.getNickname();
        }
    }

}
