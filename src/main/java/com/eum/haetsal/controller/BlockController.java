package com.eum.haetsal.controller;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.ProfileRequestDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.service.BlockService;
import com.eum.haetsal.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/haetsal-service/api/v2/block")
@RequiredArgsConstructor
@Tag(name = "Block", description = "차단 관련 api")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "x-requested-with, Authorization, Content-Type")
public class BlockController {
    private final ProfileService profileService;
    private final BlockService blockService;
    @PostMapping("")
    public ResponseEntity<APIResponse> blockedAction(@RequestBody ProfileRequestDTO.BlockProfile blockProfile , @RequestHeader("userId") String userId){
        Profile blocker = profileService.findByUser(Long.valueOf(userId));
        Profile blocked = profileService.findById(blockProfile.getProfileId()); //차단할 유저 객체

        blockService.blockedAction(blocker, blocked);
        return new ResponseEntity<>(APIResponse.of(SuccessCode.INSERT_SUCCESS,"차단 성공"), HttpStatus.CREATED);

    }
    @DeleteMapping("")
    public ResponseEntity<APIResponse> deleteBlockedAction(@RequestBody ProfileRequestDTO.BlockProfile blockProfile , @RequestHeader("userId") String userId){
        Profile blocker = profileService.findByUser(Long.valueOf(userId));
        Profile blocked = profileService.findById(blockProfile.getProfileId()); //차단할 유저 객체

        blockService.deleteBlockedAction(blocker, blocked); //차단•해제에 대한 판별

        return new ResponseEntity<>(APIResponse.of(SuccessCode.INSERT_SUCCESS,"차단 해제"), HttpStatus.CREATED);

    }
    @GetMapping()
    public void blockedList(@RequestHeader("uesrId") String userId ) {
        Profile blocker = profileService.findByUser(Long.valueOf(userId));
        List<Long> getBlocked = blockService.getMyBlockedIds(blocker);

    }
}
