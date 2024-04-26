package com.eum.haetsal.controller.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


public class ProfileRequestDTO {
    @Getter
    @Setter
    public static class CreateProfile{
        @NotEmpty
        @Schema(description = "닉네임")
        private String nickName;
        @NotEmpty
        @Pattern(regexp = "\\d{4}", message = "4자리 정수 비밀번호")
        @Schema(description = "계좌비밀번호")
        private String password;
        @NotEmpty
        @Schema(description = "이름")
        private String name;
        @NotEmpty
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형태 yyyy-MM-dd ")
        @Schema(description = "생일",example = "2001-09-12")
        private String birth;
        @Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'")
        @Schema(description = "성별",allowableValues = {"male","female"})
        private String gender;
        private String address;
        private byte[] fileByte;


    }
    @Getter
    @Setter
    public static class UpdateProfile{
        @NotEmpty(message = "닉네임을 입력하세요")
        private String nickname;

    }
    @Getter
    @Setter
    public static class BlockProfile{
        private Long userId;
    }

}
