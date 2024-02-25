package com.eum.haetsal.service;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.response.InitialResponseDTO;
import com.eum.haetsal.domain.withdrawalcategory.WithdrawalCategory;
import com.eum.haetsal.domain.withdrawalcategory.WithdrawalCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WithdrawalCategoryService {
    private final WithdrawalCategoryRepository withdrawalCategoryRepository;
    public APIResponse<List<InitialResponseDTO.WithdrawalCategoryResponse>> getCategories(){
        List<WithdrawalCategory> withdrawalCategories = withdrawalCategoryRepository.findAll();
        List<InitialResponseDTO.WithdrawalCategoryResponse> withdrawalCategoryResponses = withdrawalCategories.stream().map(InitialResponseDTO.WithdrawalCategoryResponse::new).collect(Collectors.toList());
        return APIResponse.of(SuccessCode.SELECT_SUCCESS, withdrawalCategoryResponses);

    }
}
