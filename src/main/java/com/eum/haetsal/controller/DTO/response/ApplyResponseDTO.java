package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.common.KoreaLocalDateTime;
import com.eum.haetsal.domain.apply.Apply;
import com.eum.haetsal.domain.apply.Status;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.profile.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplyResponseDTO {

    @Getter
    @Builder
    public static class ApplyListResponse {
        private Long applyId;
        private ProfileResponseDTO.UserInfo applicantInfo;
        private String createdTime;
        private Status status;
        private String introduction;
        private Long postId;
        private Boolean isAccepted;
    }
    public ApplyListResponse newApplyListResponse(MarketPost marketPost, Profile profile, Apply apply){
        ProfileResponseDTO.UserInfo applicantInfo = ProfileResponseDTO.toUserInfo(profile);
        // 한국 시간대로 포맷팅
        String formattedCreateTime = KoreaLocalDateTime.localDateTimeToKoreaZoned(apply.getCreateDate());
        return ApplyListResponse.builder()
                .applyId(apply.getApplyId())
                .applicantInfo(applicantInfo)
                .status(apply.getStatus())
                .createdTime(formattedCreateTime)
                .introduction(apply.getContent())
                .postId(marketPost.getMarketPostId())
                .isAccepted(apply.getIsAccepted()).build();
    }
}
