package com.eum.haetsal.controller;


import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.response.InitialResponseDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.service.FileService;
import com.eum.haetsal.service.ProfileService;
import com.eum.haetsal.service.UserService;
import com.eum.haetsal.service.WithdrawalCategoryService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("haetsal-service/api/v2/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","https://hanmaeul.vercel.app"}, allowedHeaders = "*")
@Slf4j
public class UserController {
    private final WithdrawalCategoryService withdrawalCategoryService;
    private final ProfileService profileService;
    private final UserService userService;
    @Hidden
    @GetMapping("/withdrawal/category")
    public ResponseEntity<APIResponse<List<InitialResponseDTO.WithdrawalCategoryResponse>>> getCategories(){
        return ResponseEntity.ok(withdrawalCategoryService.getCategories());
    }
    @Transactional
    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,@RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        profileService.removePrivacy(profile);
        userService.withdrawal(authorizationHeader,userId);
        return ResponseEntity.ok("");
    }
}
