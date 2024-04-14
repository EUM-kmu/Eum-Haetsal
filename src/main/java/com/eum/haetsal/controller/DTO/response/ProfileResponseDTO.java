package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.profile.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "프로필 정보")
    public static class ProfileResponse {
        private Long profileId;

        @Schema(description = "닉네임")
        private String nickName;
        @Schema(description = "성별",allowableValues = {"male","female"})
        private String gender;
        private String address;
        @Schema(description = "나이대",example = "20")
        private int ageRange;
        @Schema(description = "계좌정보")
        private String accountNumber;
        @Schema(description = "이미지 url")
        private String profileImage; //네이버 클라우드 Url
        @Schema(description = "치단 여부")
        private Boolean blocked;

    }

    @Getter
    @Setter
    @Builder
    public static class ProfileResponseWithToken {
        private Long profileId;
        @Schema(description = "닉네임")
        private String nickName;
        @Schema(description = "성별",allowableValues = {"male","female"})
        private String gender;
        private String address;
        @Schema(description = "나이대",example = "20")
        private int ageRange;
        @Schema(description = "계좌정보")
        private String accountNumber;
        @Schema(description = "이미지 url")
        private String profileImage; //네이버 클라우드 Url
        private UserResponse.TokenInfo tokenInfo;

    }
    public static ProfileResponseWithToken toProfileToken(Profile profile){
        LocalDate now = LocalDate.now();         // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)        int year = now.getYear();
        int thisYear = now.getYear();
        int userBirth= profile.getBirth().getYear();
        return ProfileResponseWithToken.builder()
                .profileId(profile.getProfileId())
                .nickName(profile.getNickname())
                .gender(profile.getGender())
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
                .profileId(profile.getProfileId())
                .nickName(profile.getNickname())
                .gender(profile.getGender())
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
        private String gender;
        private int ageRange;
    }
    public static UserInfo toUserInfo(Profile profile){
        LocalDate now = LocalDate.now();
        int thisYear = now.getYear();
        int userBirth= profile.getBirth().getYear();
        return UserInfo.builder()
                .profileId(profile.getProfileId())
                .nickName(profile.getNickname())
                .profileImage(profile.getProfileImage())
                .ageRange((thisYear - userBirth + 1) / 10)
                .gender(profile.getGender())
                .address(profile.getAddress()).build();

    }


}
