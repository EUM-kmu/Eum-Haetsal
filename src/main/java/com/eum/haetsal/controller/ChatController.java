package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.ChatRequestDTO;
import com.eum.haetsal.controller.DTO.response.ChatResponseDTO;
import com.eum.haetsal.controller.DTO.response.MarketPostResponseDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.service.ChatService;
import com.eum.haetsal.service.MarketPostService;
import com.eum.haetsal.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("haetsal-service/api/v2/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ProfileService profileService;
    private final MarketPostService marketPostService;
    private final ChatService chatService;
    @GetMapping("{postId}")
    public ResponseEntity<APIResponse<ChatResponseDTO.ChatInfo>> getPostInfo(@PathVariable Long postId, @RequestHeader("userId") String userId){
        Profile profile = profileService.findByUser(Long.valueOf(userId));
        MarketPostResponseDTO.MarketPostResponse marketPostResponse = marketPostService.getPostInfo(postId);
        ChatResponseDTO.ChatInfo chatInfo = new ChatResponseDTO.ChatInfo(marketPostResponse, profile);
        APIResponse apiResponse = APIResponse.of(SuccessCode.SELECT_SUCCESS, chatInfo);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("posts")
    public ResponseEntity<List<ChatResponseDTO.PostInfo>> getPostList(@RequestBody ChatRequestDTO.PostIdList postIdList){
        List<ChatResponseDTO.PostInfo> postInfos = chatService.getPostList(postIdList.getPostIdList());
        return ResponseEntity.ok(postInfos);
    }
    @GetMapping("users")
    public ResponseEntity<List<ChatResponseDTO.UserInfo>> getUserList(@RequestBody ChatRequestDTO.UserIdList userIdList ){
        List<ChatResponseDTO.UserInfo> userInfos = chatService.getUserList(userIdList.getUserIdList());
        return ResponseEntity.ok(userInfos);
    }
}
