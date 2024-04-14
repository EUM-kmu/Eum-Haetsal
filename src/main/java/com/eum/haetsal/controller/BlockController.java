package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.ErrorResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.ProfileRequestDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.service.BlockService;
import com.eum.haetsal.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/haetsal-service/api/v2/block")
@RequiredArgsConstructor
@Tag(name = "Block", description = "차단 관련 api")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "x-requested-with, Authorization, Content-Type")
public class BlockController {
    private final ProfileService profileService;
    private final BlockService blockService;
    @Operation(summary = "차단하기", description = "유저 차단")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("")
    public ResponseEntity<APIResponse> blockedAction(@RequestBody ProfileRequestDTO.BlockProfile blockProfile , @RequestHeader("userId") String userId){
        Profile blocker = profileService.findByUser(Long.valueOf(userId));
        Profile blocked = profileService.findById(blockProfile.getProfileId()); //차단할 유저 객체

        blockService.blockedAction(blocker, blocked);
        return new ResponseEntity<>(APIResponse.of(SuccessCode.INSERT_SUCCESS,"차단 성공"), HttpStatus.CREATED);

    }
    @Operation(summary = "차단 해제", description = "차단해제")@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("")
    public ResponseEntity<APIResponse> deleteBlockedAction(@RequestBody ProfileRequestDTO.BlockProfile blockProfile , @RequestHeader("userId") String userId){
        Profile blocker = profileService.findByUser(Long.valueOf(userId));
        Profile blocked = profileService.findById(blockProfile.getProfileId()); //차단할 유저 객체

        blockService.deleteBlockedAction(blocker, blocked); //차단•해제에 대한 판별

        return new ResponseEntity<>(APIResponse.of(SuccessCode.INSERT_SUCCESS,"차단 해제"), HttpStatus.CREATED);

    }

}
