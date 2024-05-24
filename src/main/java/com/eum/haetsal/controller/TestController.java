package com.eum.haetsal.controller;

import com.eum.haetsal.service.DTO.FcmMessage;
import com.eum.haetsal.service.FcmService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping()
@RequiredArgsConstructor
public class TestController {
    private final FcmService fcmService;
    @GetMapping("/test")
    public String testFCM(@RequestParam String token){
        FcmMessage.Test fcmMessage = new FcmMessage.Test(token);
        return fcmService.sendNotification(fcmMessage.getToken(),fcmMessage.getTitle(),fcmMessage.getMessage());

    }
}
