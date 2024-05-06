package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.common.KoreaLocalDateTime;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.marketpost.Status;
import com.eum.haetsal.domain.profile.Profile;
import lombok.Getter;
import lombok.Setter;

public class ChatResponseDTO {
    @Getter
    @Setter
    public static class PostInfo{
        private Long postId;
        private String title;
        private String content;
        private String location;
        private String startDate;
        private String  createdDate;
        private Status status;
        private Long dealId;
        private Long pay;
        private boolean deleted;
        private UserInfo userInfo;

        public PostInfo(MarketPost marketPost) {
            this.postId = marketPost.getMarketPostId();
            this.title = marketPost.getTitle();
            this.content = marketPost.getContent();
            this.location = marketPost.getLocation();
            this.startDate =KoreaLocalDateTime.dateToKoreaZone(marketPost.getStartDate());
            this.createdDate =KoreaLocalDateTime.localDateTimeToKoreaZoned(marketPost.getCreateDate());
            this.status = marketPost.getStatus();
            this.pay = marketPost.getPay();
            this.dealId = marketPost.getDealId();
            this.deleted = marketPost.isDeleted();
            this.userInfo = new UserInfo(marketPost.getProfile());
        }
    }
    @Getter
    @Setter
    public static class UserInfo{
        private Long userId;
        private Long profileId;
        private String profileImage;
        private String nickName;
        private String address;
        private String accountNumber;
        private boolean isDeleted;
//        private List<Map<Long,Boolean>> Block ;


        public UserInfo(Profile profile) {
            this.userId = profile.getUser().getUserId();
            this.profileId = profile.getProfileId();
            this.profileImage = profile.getProfileImage();
            this.nickName = profile.getNickname();
            this.address = profile.getAddress();
            this.accountNumber = profile.getUser().getAccountNumber();
            this.isDeleted = profile.isDeleted();
        }
    }

}