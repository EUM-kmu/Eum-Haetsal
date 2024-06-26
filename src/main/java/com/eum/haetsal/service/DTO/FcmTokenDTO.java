package com.eum.haetsal.service.DTO;

import com.eum.haetsal.domain.fcmtoken.FcmToken;
import lombok.Data;

@Data
public class FcmTokenDTO {
    private Long userId;
    private String token;

    public FcmTokenDTO(FcmToken fcmToken) {
        this.userId = fcmToken.getUser().getUserId();
        this.token = fcmToken.getToken();
    }
}
