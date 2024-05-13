package com.eum.haetsal.client;

import com.eum.haetsal.client.DTO.BaseResponseEntity;
import com.eum.haetsal.client.DTO.ChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "chat-service", url = " https://chat.haetsal.com/")
public interface ChatClient {
    @GetMapping("/api/haetsal/oneToOneChat")
    List<ChatResponse> getChatList(@RequestParam String myProfileId, @RequestParam String otherProfileId);
}
