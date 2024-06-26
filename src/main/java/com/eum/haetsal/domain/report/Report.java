package com.eum.haetsal.domain.report;

import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private Long reportId;
    @Column
    private String reason;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "market_post_id")
    private MarketPost marketPost;

    public static Report toEntity(String reason, Profile profile, MarketPost marketPost) {
        return Report.builder()
                .reason(reason)
                .profile(profile)
                .marketPost(marketPost).build();
    }

}
