package com.eum.haetsal.client;

import com.eum.haetsal.client.DTO.BaseResponseEntity;
import com.eum.haetsal.client.DTO.ChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "chat-service", url = "http://223.130.141.109:5000/")
public interface ChatClient {
    @GetMapping("/api/haetsal/oneToOneChat")
    List<ChatResponse> getChatList(@RequestParam String myId,
                                                       @RequestParam String theOtherId);
}
