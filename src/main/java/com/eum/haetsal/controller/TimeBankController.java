package com.eum.haetsal.controller;
import com.eum.haetsal.controller.DTO.request.TimeBankRequestDTO;
import com.eum.haetsal.controller.DTO.response.TimeBankResponseDTO;
import com.eum.haetsal.service.TimeBankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "TimeBank", description = "타임뱅크 어플용 API")
@RestController
@AllArgsConstructor
@RequestMapping("/haetsal-service/api/v2/timebank")
public class TimeBankController {

    private final TimeBankService timeBankService;

    @Operation(summary = "타임뱅크용 API", description = "유저 정보를 봅니다.")
    @PostMapping("/users")
    public ResponseEntity<List<TimeBankResponseDTO.UserInfo>> getUserList(@RequestBody TimeBankRequestDTO.AccountNumberList accountNumberList){
        List<TimeBankResponseDTO.UserInfo> userInfos = timeBankService.getUserList(accountNumberList.getAccountNumberList());
        return ResponseEntity.ok(userInfos);
    }
}
