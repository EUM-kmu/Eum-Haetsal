package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.ErrorResponse;
import com.eum.haetsal.controller.DTO.request.ApplyRequestDTO;
import com.eum.haetsal.controller.DTO.response.ApplyResponseDTO;
import com.eum.haetsal.controller.DTO.response.MarketPostResponseDTO;
import com.eum.haetsal.controller.DTO.response.MarketPostResponseDTO.MarketPostResponse;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.service.ApplyService;
import com.eum.haetsal.service.MarketPostService;
import com.eum.haetsal.service.ProfileService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("haetsal-service/api/v2/market/post")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ApplyController {
    private final ApplyService applyService;
    private final ProfileService profileService;
    private final MarketPostService marketPostService;

    /**
     * 지원하기
     * @param postId : 지원할 게시글 id
     * @param apply : 지원 폼
     * @return
     */
    @PostMapping("/{postId}/apply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<APIResponse> apply(@PathVariable Long postId, @RequestBody ApplyRequestDTO.Apply apply, @RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        return new ResponseEntity<>(applyService.doApply(postId,apply, profile), HttpStatus.CREATED);
    }

    /**
     * 지원취소
     * @param postId
     * @param applyId
     * @param userId
     * @return
     */
    @DeleteMapping("/{postId}/apply/{applyId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<APIResponse> unapply(@PathVariable Long postId,@PathVariable Long applyId, @RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        return new ResponseEntity<>(applyService.unApply(postId,applyId, profile), HttpStatus.CREATED);
    }

    /**
     * 내 게시글의 지원자 리스트 조회
     * @param postId : 지원자리스트를 보고싶은 게시글 id
     * @return : 지원자 정보, 지원정보(지원 id, 게시글 id, 한줄 소개, 수락 여부)
     */
    @GetMapping("/{postId}/apply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<APIResponse<List<ApplyResponseDTO.ApplyListResponse>>> getApplyList(@PathVariable Long postId){
        return ResponseEntity.ok(applyService.getApplyList(postId));
    }

    /**
     * 지원 수락하기
     * @param acceptList : 수락할 지원 리스크
     * @return
     */
    @PostMapping("/{postId}/accept")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식 혹은 요청 콘텐츠가 올바르지 않을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 시간 만료, 형식 오류,로그아웃한 유저 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "헤더에 토큰이 들어가있지 않은 경우",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "외부 API 요청 실패, 정상적 수행을 할 수 없을 때,",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<APIResponse> acceptByPost(@PathVariable Long postId, @RequestHeader("userId") String userId,@RequestBody ApplyRequestDTO.AcceptList acceptList){

        Profile profile = profileService.findByUser(Long.valueOf(userId));

        MarketPostResponse marketPost = marketPostService.getMarketPosts(postId, profile).getData().getMarketPostResponse();
        Long dealId = marketPost.getDealId();
        if (acceptList.getApplyIds().size() > marketPost.getMaxNumOfPeople())
            throw new IllegalArgumentException("최대 신청자 수를 넘었습니다");

        return ResponseEntity.ok(applyService.accept(postId, dealId,acceptList.getApplyIds(),profile));
    }


}
