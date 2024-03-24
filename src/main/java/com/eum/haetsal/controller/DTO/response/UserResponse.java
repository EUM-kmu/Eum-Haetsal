package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResponse {
    @Builder
    @Getter
    @AllArgsConstructor
    @Schema(description = "토큰 정보")
    public static class TokenInfo {
        @Schema(description = "토큰 타입" ,example ="Bearer" )
        private String grantType;
        @Schema(description = "엑세스 토큰")
        private String accessToken;
        @Schema(description = "리프레시 토큰")
        private String refreshToken;
        @Schema(description = "토큰 만료 시간")
        private Long refreshTokenExpirationTime;
        private Role role;
    }
}
