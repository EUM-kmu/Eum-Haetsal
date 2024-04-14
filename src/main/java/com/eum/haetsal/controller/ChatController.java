package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.ErrorResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.ChatRequestDTO;
import com.eum.haetsal.controller.DTO.request.MarketPostRequestDTO;
import com.eum.haetsal.controller.DTO.response.ChatResponseDTO;
import com.eum.haetsal.controller.DTO.response.MarketPostResponseDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.service.ChatService;
import com.eum.haetsal.service.MarketPostService;
import com.eum.haetsal.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("haetsal-service/api/v2/")
@Tag(name = "Chat" ,description = "chat 서버용 api")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("chat/posts")
    public ResponseEntity<List<ChatResponseDTO.PostInfo>> getPostList(@RequestBody ChatRequestDTO.PostIdList postIdList){
        List<ChatResponseDTO.PostInfo> postInfos = chatService.getPostList(postIdList.getPostIdList());
        return ResponseEntity.ok(postInfos);
    }
    @PostMapping("chat/users")
    public ResponseEntity<List<ChatResponseDTO.UserInfo>> getUserList(@RequestBody ChatRequestDTO.UserIdList userIdList ){
        List<ChatResponseDTO.UserInfo> userInfos = chatService.getUserList(userIdList.getUserIdList());
        return ResponseEntity.ok(userInfos);
    }
}
