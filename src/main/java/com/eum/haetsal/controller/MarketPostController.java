package com.eum.haetsal.controller;


import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.request.MarketPostRequestDTO;
import com.eum.haetsal.controller.DTO.request.enums.MarketType;
import com.eum.haetsal.controller.DTO.request.enums.ServiceType;
import com.eum.haetsal.controller.DTO.response.MarketPostResponseDTO;
import com.eum.haetsal.domain.marketpost.Status;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.service.BlockService;
import com.eum.haetsal.service.FileService;
import com.eum.haetsal.service.MarketPostService;
import com.eum.haetsal.service.ProfileService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/market/post")
@RequiredArgsConstructor
@Slf4j
public class  MarketPostController {
    private final MarketPostService marketPostService;
    private final BlockService blockService;
    private final FileService fileService;
    private final ProfileService profileService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping(consumes =  {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse<MarketPostResponseDTO.MarketPostResponse>> create(@RequestPart(value = "request") @Validated MarketPostRequestDTO.MarketCreate marketCreate, @RequestPart(value = "files") List<MultipartFile> multipartFiles,@RequestHeader("userId") String userId) throws ParseException {
        fileService.uploadFiles(multipartFiles, "marketpost");
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        return new ResponseEntity<>(marketPostService.create(marketCreate, profile), HttpStatus.CREATED);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("/{postId}")
    public  ResponseEntity<APIResponse> delete(@PathVariable Long postId, @RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        return ResponseEntity.ok(marketPostService.delete(postId,profile));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/{postId}")
    public  ResponseEntity<APIResponse<MarketPostResponseDTO.MarketPostResponse>> update(@PathVariable Long postId, @RequestBody @Validated MarketPostRequestDTO.MarketUpdate marketUpdate, @RequestHeader("userId") String userId) throws ParseException {
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        return ResponseEntity.ok(marketPostService.update(postId,marketUpdate,profile));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/{postId}/status")
    public  ResponseEntity<APIResponse> updateState(@PathVariable Long postId, @RequestBody MarketPostRequestDTO.UpdateStatus status, @RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        return ResponseEntity.ok(marketPostService.updateState(postId,status.getStatus(), profile));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("{postId}")
    public  ResponseEntity<APIResponse<MarketPostResponseDTO.MarketPostWithComment>> findById(@PathVariable Long postId, @RequestHeader("userId") String userId, Pageable pageable){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        return ResponseEntity.ok(marketPostService.getMarketPosts(postId,profile));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("")
    public  ResponseEntity<APIResponse<List<MarketPostResponseDTO.MarketPostResponse>>> findByFilter(@RequestParam(value = "search",required = false) String keyword, @RequestParam(name = "category",required = false) String category,
                                                                                                     @RequestParam(name = "marketType",required = false) MarketType marketType, @RequestParam(name = "status",required = false) Status status,
                                                                                                     @PageableDefault Pageable pageable, @RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        List<Profile> blockedUsers = blockService.getBlockedUser(profile);
        return ResponseEntity.ok(marketPostService.findByFilter(keyword,category,marketType,status,pageable,blockedUsers));
    }


    /**
     * 내활동 타입별 게시글 조회 : 내가 스크랩 한 글, 내가 작성한 게시글, 내 지원 글
     * @param serviceType : scrap, postlist,apply
     * @param userId
     * @return : 게시글 정보
     */

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/user-activity/{serviceType}")
    public ResponseEntity<APIResponse<List<MarketPostResponseDTO.MarketPostResponse>>> findByServiceType(@PathVariable ServiceType serviceType, @RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        List<Profile> blockedUser = blockService.getBlockedUser(profile);
        return ResponseEntity.ok(marketPostService.findByServiceType(serviceType,profile,blockedUser));
    }






}