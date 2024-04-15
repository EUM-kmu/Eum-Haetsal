package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.marketpost.Status;
import com.eum.haetsal.domain.profile.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class ChatResponseDTO {
    @Getter
    @Setter
    public static class PostInfo{
        private Long postId;
        private Status status;
        private String title;
        private Long pay;
        private Long dealId;

        public PostInfo(MarketPost marketPost) {
            this.postId = marketPost.getMarketPostId();
            this.status = marketPost.getStatus();
            this.title = marketPost.getTitle();
            this.pay = marketPost.getPay();
            this.dealId = marketPost.getDealId();
        }
    }
    @Getter
    @Setter
    public static class UserInfo{
        private Long userId;
        private Long profileId;
        private String profileImage;
        private String nickName;
        private String accountNumber;
//        private List<Map<Long,Boolean>> Block ;


        public UserInfo(Profile profile) {
            this.userId = profile.getUser().getUserId();
            this.profileId = profile.getProfileId();
            this.profileImage = profile.getProfileImage();
            this.nickName = profile.getNickname();
            this.accountNumber = profile.getUser().getAccountNumber();

        }
    }

}
