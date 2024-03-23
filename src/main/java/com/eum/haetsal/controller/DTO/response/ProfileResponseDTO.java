package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.profile.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j

public class ProfileResponseDTO {

    @Getter
    @Setter
    @Builder
    public static class ProfileResponse {
        private String nickName;
        private String sex;
        private String address;
        private int ageRange;
        private String accountNumber;
        private String profileImage; //네이버 클라우드 Url

    }

    @Getter
    @Setter
    @Builder
    public static class ProfileResponseWithToken {
        private String nickName;
        private String sex;
        private String address;
        private int ageRange;
        private String accountNumber;
        private String profileImage; //네이버 클라우드 Url
        private UserResponse.TokenInfo tokenInfo;

    }
    public static ProfileResponseWithToken toProfileToken(Profile profile){
        LocalDate now = LocalDate.now();         // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)        int year = now.getYear();
        int thisYear = now.getYear();
        int userBirth= profile.getBirth().getYear();
        return ProfileResponseWithToken.builder()
                .nickName(profile.getNickname())
                .sex(profile.getSex())
                .ageRange((thisYear - userBirth + 1) / 10)
                .address("주소였던것")
                .accountNumber(profile.getUser().getAccountNumber())
                .profileImage(profile.getProfileImage())
                .build();
    }

    public static ProfileResponse toProfileResponse(Profile profile){
        LocalDate now = LocalDate.now();         // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)        int year = now.getYear();
        int thisYear = now.getYear();
        int userBirth= profile.getBirth().getYear();
        return ProfileResponse.builder()
                .nickName(profile.getNickname())
                .sex(profile.getSex())
                .ageRange((thisYear - userBirth + 1) / 10)
                .address("주소였던것")
                .accountNumber(profile.getUser().getAccountNumber())
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
