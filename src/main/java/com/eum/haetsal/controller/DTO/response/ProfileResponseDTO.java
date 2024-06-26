package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.profile.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Base64;

@Component
@Slf4j

public class ProfileResponseDTO {

    @Getter
    @Setter
    @Builder
    @Schema(description = "프로필 정보")
    public static class ProfileResponse {
        private Long userId;

        @Schema(description = "닉네임")
        private String nickName;
        @Schema(description = "성별",allowableValues = {"male","female"})
        private String gender;
        private String address;
        @Schema(description = "나이대",example = "20")
        private int ageRange;
        private String birth;
        @Schema(description = "계좌정보")
        private String accountNumber;
        @Schema(description = "이미지 url")
        private String profileImage; //네이버 클라우드 Urlprivate String profileImage; //네이버 클라우드 Url
        @Schema(description = "치단 여부")
        private Boolean blocked;
        private int dealCount;


    }

    @Getter
    @Setter
    @Builder
    public static class ProfileResponseWithToken {
        private Long userId;
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
        private int dealCount;
        private UserResponse.TokenInfo tokenInfo;

    }
    public static ProfileResponseWithToken toProfileToken(Profile profile){
        LocalDate now = LocalDate.now();         // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)        int year = now.getYear();
        int thisYear = now.getYear();
        int userBirth= profile.getBirth().getYear();
        return ProfileResponseWithToken.builder()
                .userId(profile.getUser().getUserId())
                .nickName(profile.getNickname())
                .gender(profile.getGender())
                .ageRange((thisYear - userBirth + 1) / 10)
                .address(profile.getAddress())
                .accountNumber(profile.getUser().getAccountNumber())
                .dealCount(profile.getDealCount())
                .profileImage(profile.getProfileImage())
                .build();
    }

    public static ProfileResponse toProfileResponse(Profile profile){
        LocalDate now = LocalDate.now();         // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)        int year = now.getYear();
        int thisYear = now.getYear();
        int userBirth= profile.getBirth().getYear();
        return ProfileResponse.builder()
                .userId(profile.getUser().getUserId())
                .nickName(profile.getNickname())
                .gender(profile.getGender())
                .ageRange((thisYear - userBirth + 1) / 10)
                .birth(String.valueOf(profile.getBirth()))
                .address(profile.getAddress())
                .dealCount(profile.getDealCount())
                .accountNumber(profile.getUser().getAccountNumber())
                .profileImage(profile.getProfileImage())
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class UserInfo{
        private Long userId;
        private String nickName;
        private String profileImage;
        private String address;
        private String gender;
        private int ageRange;
        private int dealCount;
    }
    public static UserInfo toUserInfo(Profile profile){
        LocalDate now = LocalDate.now();
        int thisYear = now.getYear();
        int userBirth= profile.getBirth().getYear();
        return UserInfo.builder()
                .userId(profile.getUser().getUserId())
                .nickName(profile.getNickname())
                .profileImage(profile.getProfileImage())
                .ageRange((thisYear - userBirth + 1) / 10)
                .dealCount(profile.getDealCount())
                .gender(profile.getGender())
                .address(profile.getAddress()).build();

    }


}
