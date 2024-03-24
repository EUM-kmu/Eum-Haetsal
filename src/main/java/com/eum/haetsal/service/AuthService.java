package com.eum.haetsal.service;

import com.eum.haetsal.client.AuthClient;
import com.eum.haetsal.controller.DTO.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthClient authClient;

    public UserResponse.TokenInfo getToken(String userId){
        UserResponse.TokenInfo tokenInfo =  authClient.updateToken(userId);
        return tokenInfo;
    }

}

