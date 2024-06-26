package com.eum.haetsal.controller.DTO.response;


import com.eum.haetsal.common.KoreaLocalDateTime;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.marketpost.Status;
import com.eum.haetsal.domain.profile.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarketPostResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class MarketPostResponse{
        private Long postId;
        private String title;
        private String content;
        private String location;
        @Schema(description = "시작시간",example = "2023-11-29T00:17:08+0900")
        private String startDate;
        @Schema(description = "금액")
        private Long pay;
        @Schema(description = "소요 시간")
        private int volunteerTime;
        @Schema(description = "생성 시간",example = "2023-11-29T00:17:08+0900")
        private String  createdDate;
        @Schema(description = "진행도")
        private Status status;
        @Schema(description = "현재 지원자")
        private int currentApplicant;
        @Schema(description = "최대 지원자")
        private int maxNumOfPeople;
        @Schema(description = "조회수")
        private Long viewsCount;
        private Long dealId;
        private ProfileResponseDTO.UserInfo writerInfo;
        private boolean deleted;
        private Long categoryId;

    }
    @Builder
    @Getter
    @AllArgsConstructor
    public static class MarketPostDetail {
        private UserCurrentStatus userCurrentStatus;
        private MarketPostResponse marketPostResponse;
    }
    @Builder
    @Getter
    @Setter
    private static class UserCurrentStatus{
        @Schema(description = "작성자인지")
        private boolean isWriter;
        @Schema(description = "신고여부")
        private boolean isReport;
        @Schema(description = "지원여부")
        private boolean isApplicant;
        @Schema(description = "지원id")
        private Long applyId;
        private com.eum.haetsal.domain.apply.Status applyStatus;
    }
    public static MarketPostResponse toMarketPostResponse(MarketPost marketPost, int currentApplicant){
        String createdTime = KoreaLocalDateTime.localDateTimeToKoreaZoned(marketPost.getCreateDate());
        String startTime = KoreaLocalDateTime.dateToKoreaZone(marketPost.getStartDate());
        return MarketPostResponse.builder()
                .deleted(marketPost.isDeleted())
                .postId(marketPost.getMarketPostId())
                .title(marketPost.getTitle())
                .createdDate(createdTime)
                .startDate(startTime)
                .content(marketPost.getContent())
                .pay(marketPost.getPay())
                .volunteerTime(marketPost.getVolunteerTime())
                .viewsCount(marketPost.getViewsCount())
                .location(marketPost.getLocation())
                .writerInfo(ProfileResponseDTO.toUserInfo(marketPost.getProfile()))
                .dealId(marketPost.getDealId())
                .status(marketPost.getStatus())
                .currentApplicant(currentApplicant)
                .maxNumOfPeople(marketPost.getMaxNumOfPeople())
                .categoryId(marketPost.getMarketCategory().getCategoryId())
                .build();
    }
    public static MarketPostDetail toMarketPostDetails(Profile profile, MarketPost marketPost, Boolean isApply, com.eum.haetsal.domain.apply.Status tradingStatus,Long applyId,boolean report){
        UserCurrentStatus userCurrentStatus = UserCurrentStatus.builder().isApplicant(isApply).applyId(applyId).isWriter(profile==marketPost.getProfile()).applyStatus(tradingStatus).isReport(report).build();
        MarketPostResponse marketPostResponse = toMarketPostResponse(marketPost, marketPost.getApplies().size());
        return MarketPostDetail.builder()

                .userCurrentStatus(userCurrentStatus)
                .marketPostResponse(marketPostResponse)
                .build();
    }

}
