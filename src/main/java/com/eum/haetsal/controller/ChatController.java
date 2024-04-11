package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.response.ChatResponseDTO;
import com.eum.haetsal.controller.DTO.response.MarketPostResponseDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.service.MarketPostService;
import com.eum.haetsal.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("haetsal-service/api/v2/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ProfileService profileService;
    private final MarketPostService marketPostService;
    @GetMapping("{postId}")
    public ResponseEntity<APIResponse<ChatResponseDTO.ChatInfo>> getPostInfo(@PathVariable Long postId, @RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        MarketPostResponseDTO.MarketPostResponse marketPostResponse = marketPostService.getPostInfo(postId);
        ChatResponseDTO.ChatInfo chatInfo = new ChatResponseDTO.ChatInfo(marketPostResponse, profile);
        APIResponse apiResponse = APIResponse.of(SuccessCode.SELECT_SUCCESS, chatInfo);
        return ResponseEntity.ok(apiResponse);
    }
}
