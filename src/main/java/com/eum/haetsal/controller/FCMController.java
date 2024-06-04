package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.TokenDTO;
import com.eum.haetsal.service.DTO.FcmMessage;
import com.eum.haetsal.service.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j
@RequestMapping("haetsal-service/api/v2/notification")
@RequiredArgsConstructor
public class FCMController {
    private final FcmService fcmService;
    @GetMapping("/testfcm")
    public String testFCM(@RequestParam String token){
        FcmMessage.Test fcmMessage = new FcmMessage.Test(token);
        return fcmService.sendTestNotification(fcmMessage.getToken(),fcmMessage.getTitle(),fcmMessage.getMessage());

    }
//    @PostMapping("")
//    public ResponseEntity<APIResponse> updateFcm(@RequestBody TokenDTO token, @RequestHeader("userId") String userId){
//        fcmService.updateToken(token.getFcmToken(), Long.valueOf(userId));
//        return ResponseEntity.ok(APIResponse.of(SuccessCode.UPDATE_SUCCESS, "fcm 토큰 등록"));
//    }
}
