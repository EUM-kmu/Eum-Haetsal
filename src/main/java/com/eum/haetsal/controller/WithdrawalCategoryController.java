package com.eum.haetsal.controller;


import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.controller.DTO.response.InitialResponseDTO;
import com.eum.haetsal.service.WithdrawalCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("haetsal-service/api/v2/withdrawal")
@RequiredArgsConstructor
public class WithdrawalCategoryController {
    private final WithdrawalCategoryService withdrawalCategoryService;
    @GetMapping("/category")
    public ResponseEntity<APIResponse<List<InitialResponseDTO.WithdrawalCategoryResponse>>> getCategories(){
        return ResponseEntity.ok(withdrawalCategoryService.getCategories());
    }
}
