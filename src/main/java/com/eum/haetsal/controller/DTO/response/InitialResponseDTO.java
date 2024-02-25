package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.domain.withdrawalcategory.WithdrawalCategory;
import lombok.Getter;
import lombok.Setter;

public class InitialResponseDTO {

    @Getter
    @Setter
    public static class WithdrawalCategoryResponse{
        private Long categoryId;
        private String content;

        public WithdrawalCategoryResponse(WithdrawalCategory withdrawalCategory) {
            this.categoryId = withdrawalCategory.getWithdrawalCategoryId();
            this.content = withdrawalCategory.getContent();
        }
    }



}
