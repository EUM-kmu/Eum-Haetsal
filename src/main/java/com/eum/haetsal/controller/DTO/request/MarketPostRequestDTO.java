package com.eum.haetsal.controller.DTO.request;

import com.eum.haetsal.domain.marketpost.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


public class MarketPostRequestDTO {

    @Setter
    @Getter
    public static class MarketCreate {
        @NotEmpty(message = "제목을 입력하세요")
        private String title;
//        @NotEmpty(message = "내용을 입력하세요")
        private String content;

        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]\\d{4}", message = "yyyy-MM-dd'T'HH:mm:ssZ 형식이여야 합니다")
        @Schema(description = "시작시간",example ="2023-11-29T00:17:08+0900" )
        private String startDate;

        @NotEmpty(message = "상세 주소를 작성해주세요")
        private String location;

        @Positive(message = "참여 시간은 양수여야 합니다")
        private int volunteerTime;
//        @NotNull(message = "null이 오면 안됩니다")
//        private MarketType marketType;

        @Min(value = 1, message = "최소값은 1이어야 합니다.")
        @Max(value = 50, message = "최대값은 50이어야 합니다.")
        private int maxNumOfPeople;

//        @NotNull(message = "카테고리를 입력해주세요")
//        private String category;
    }
    @Setter
    @Getter
    public static class MarketUpdate {
        @NotEmpty(message = "제목을 입력하세요")
        private String title;
        @NotEmpty(message = "내용을 입력하세요")
        private String content;
//        @NotNull(message = "null이 오면 안됩니다")
//        private Slot slot;
        @Schema(description = "시작시간",example ="2023-11-29T00:17:08+0900" )
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]\\d{4}", message = "yyyy-MM-dd'T'HH:mm:ssZ 형식이여야 합니다")
        private String startDate;
        @NotEmpty(message = "상세 주소를 작성해주세요")
        private String location;
        @Min(value = 1, message = "최소값은 1이어야 합니다.")
        @Max(value = 50, message = "최대값은 50이어야 합니다.")
        private int maxNumOfPeople;
        @Positive(message = "참여시간은 양수여야함")
        private int volunteerTime;

    }
    @Getter
    @Setter
    public static class UpdateStatus{
        private Status status;
    }
}
