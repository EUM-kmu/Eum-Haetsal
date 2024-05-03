package com.eum.haetsal.controller;

import com.eum.haetsal.controller.DTO.request.ChatRequestDTO;
import com.eum.haetsal.controller.DTO.response.ChatResponseDTO;
import com.eum.haetsal.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ChatResponseDTO.UserInfo>> getUserList(@RequestBody ChatRequestDTO.ProfileIdList profileIdList){
        List<ChatResponseDTO.UserInfo> userInfos = chatService.getUserList(profileIdList.getProfileIdList());
        return ResponseEntity.ok(userInfos);
    }
}
