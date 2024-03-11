package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.profile.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component

public class ProfileResponseDTO {

    @Getter
    @Setter
    @Builder
    public static class ProfileResponse {
        private String nickName;
        private String address;
        private String accountNumber;
        private String profileImage; //네이버 클라우드 Url

    }

    public static ProfileResponse toProfileResponse(Profile profile){
        return ProfileResponse.builder()
                .nickName(profile.getNickname())
                .address("주소였던것")
                .profileImage(profile.getProfileImage())
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class UserInfo{
        private Long profileId;
        private String nickName;
        private String profileImage;
        private String address;
    }
    public static UserInfo toUserInfo(Profile profile){
        return UserInfo.builder()
                .profileId(profile.getProfileId())
                .nickName(profile.getNickname())
                .profileImage(profile.getProfileImage())
                .address("주소였던것").build();
    }


}
