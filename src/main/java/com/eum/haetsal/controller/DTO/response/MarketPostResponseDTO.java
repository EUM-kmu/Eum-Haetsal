package com.eum.haetsal.controller.DTO.response;


import com.eum.haetsal.common.KoreaLocalDateTime;
import com.eum.haetsal.controller.DTO.request.enums.MarketType;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.marketpost.Slot;
import com.eum.haetsal.domain.marketpost.Status;
import com.eum.haetsal.domain.profile.Profile;
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
        private String  createdDate;
        private Status status;
        private String startDate;
        private Slot slot;
        private String location;
        private Long pay;
        private int volunteerTime;
        private MarketType marketType;
        private int currentApplicant;
        private int maxNumOfPeople;
        private String category;

        private Long dealId;

    }
    @Builder
    @Getter
    @AllArgsConstructor
    public static class MarketPostWithComment {
        private ProfileResponseDTO.UserInfo writerInfo;
        private UserCurrentStatus userCurrentStatus;
        private MarketPostResponse marketPostResponse;
    }
    @Builder
    @Getter
    @Setter
    private static class UserCurrentStatus{
        private Boolean isWriter;
        private Boolean isApplicant;
//        private Boolean isScrap;
        private com.eum.haetsal.domain.apply.Status applyStatus;
    }
    public static MarketPostResponse toMarketPostResponse(MarketPost marketPost, int currentApplicant){
        String createdTime = KoreaLocalDateTime.localDateTimeToKoreaZoned(marketPost.getCreateDate());
        String startTime = KoreaLocalDateTime.dateToKoreaZone(marketPost.getStartDate());
        return MarketPostResponse.builder()
                .postId(marketPost.getMarketPostId())
                .title(marketPost.getTitle())
                .createdDate(createdTime)
                .startDate(startTime)
                .slot(marketPost.getSlot())
                .content(marketPost.getContent())
                .pay(marketPost.getPay())
                .volunteerTime(marketPost.getVolunteerTime())
                .marketType(marketPost.getMarketType())
                .location(marketPost.getLocation())
                .category(marketPost.getMarketCategory().getContents())
                .dealId(marketPost.getDealId())
                .status(marketPost.getStatus())
                .currentApplicant(currentApplicant)
                .maxNumOfPeople(marketPost.getMaxNumOfPeople())
                .build();
    }
    public static MarketPostWithComment toMarketPostDetails(Profile profile, MarketPost marketPost, Boolean isApply, com.eum.haetsal.domain.apply.Status tradingStatus){
        UserCurrentStatus userCurrentStatus = UserCurrentStatus.builder().isApplicant(isApply).isWriter(profile==marketPost.getProfile()).applyStatus(tradingStatus).build();
        MarketPostResponse marketPostResponse = toMarketPostResponse(marketPost, marketPost.getApplies().size());
        return MarketPostWithComment.builder()
                .writerInfo(ProfileResponseDTO.toUserInfo(marketPost.getProfile()))
                .userCurrentStatus(userCurrentStatus)
                .marketPostResponse(marketPostResponse)
                .build();
    }
}
