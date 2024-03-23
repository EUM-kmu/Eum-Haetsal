package com.eum.haetsal.controller;


import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.ErrorResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.ProfileRequestDTO;
import com.eum.haetsal.controller.DTO.response.ProfileResponseDTO;
import com.eum.haetsal.controller.DTO.response.UserResponse;
import com.eum.haetsal.service.AuthService;
import com.eum.haetsal.service.FileService;
import com.eum.haetsal.service.ProfileService;
import com.eum.haetsal.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@RestController
@Slf4j
@RequestMapping()
@RequiredArgsConstructor
@Tag(name = "ProfileController", description = "프로필 관련 api")
@CrossOrigin("*")
public class   ProfileController {
    private final ProfileService profileService;
    private final FileService fileService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping(path = "/auth-service/api/v2/profile",consumes =  {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<APIResponse<ProfileResponseDTO.ProfileResponseWithToken>> createProfile(@RequestPart(value = "request") @Validated ProfileRequestDTO.CreateProfile createProfile, @RequestPart(value = "file",required = false) MultipartFile multipartFile, @RequestHeader("userId") String userId) throws ParseException {
        // 이미 존재하는 userId일 경우
        // 1. 이미 프로필이 있는 회원 -> 에러
        // 2. 프로필이 없는 경우(탈퇴했던 유저) -> 프로필만 재 생성, 계좌는 이미 존재

        userService.create(Long.valueOf(userId), createProfile.getPassword());
        UserResponse.TokenInfo tokenInfo = authService.getToken(userId);
        ProfileResponseDTO.ProfileResponseWithToken profileResponseWithToken = profileService.create(createProfile, Long.valueOf(userId),multipartFile);
        profileResponseWithToken.setTokenInfo(tokenInfo);
        return new ResponseEntity<>( APIResponse.of(SuccessCode.INSERT_SUCCESS, profileResponseWithToken), HttpStatus.CREATED);
    }

    /**
     * 프로필 조회
     * @return
     */
    @GetMapping("/haetsal-service/api/v2/profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<APIResponse<ProfileResponseDTO.ProfileResponse>> getMyProfile(@RequestHeader("userId") String userId){
        return ResponseEntity.ok(profileService.getMyProfile( Long.valueOf(userId)));
    }

    @PutMapping(path = "/haetsal-service/api/v2/profile",consumes =  {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<APIResponse> updateMyProfile(@RequestPart(value="request") @Validated ProfileRequestDTO.UpdateProfile updateProfile, @RequestPart(value = "file") MultipartFile multipartFile,@RequestHeader("userId") String userId){

        return ResponseEntity.ok(profileService.updateMyProfile(updateProfile, Long.valueOf(userId), multipartFile));
    }


}
