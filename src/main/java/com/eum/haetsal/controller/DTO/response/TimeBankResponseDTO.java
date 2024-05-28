package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.profile.Profile;
import lombok.Getter;
import lombok.Setter;

public class TimeBankResponseDTO {
    @Getter
    @Setter
    public static class UserInfo{
        private Long userId;
        private String nickName;
        private String profileImage;

        public UserInfo(Profile profile) {
            this.userId = profile.getUser().getUserId();
            this.nickName = profile.getNickname();
            this.profileImage = profile.getProfileImage();
        }
    }
}
