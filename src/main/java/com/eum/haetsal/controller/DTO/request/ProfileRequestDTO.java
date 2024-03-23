package com.eum.haetsal.controller.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


public class ProfileRequestDTO {
    @Getter
    @Setter
    public static class CreateProfile{
//        @NotEmpty(message = "닉네임을 입력하세요")
        private String nickname;
        private String password;
        private String name;
        private String birth;
        private String sex;


    }
    @Getter
    @Setter
    public static class UpdateProfile{
        @NotEmpty(message = "닉네임을 입력하세요")
        private String nickname;

    }

}
