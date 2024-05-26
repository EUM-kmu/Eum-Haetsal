package com.eum.haetsal.client;

import com.eum.haetsal.controller.DTO.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "http://175.45.203.201:8081")
public interface AuthClient {
    @PutMapping("auth-service/api/v2/token")
    UserResponse.TokenInfo updateToken(@RequestHeader("userId") String userId) ;
    @PostMapping("auth-service/api/v2/withdrawal")
    void withdrawal(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader("userId") String userId) ;
}
