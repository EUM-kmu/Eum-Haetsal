package com.eum.haetsal.domain.apply;

import com.eum.haetsal.common.BaseTimeEntity;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.profile.Profile;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Apply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @Column
    private Boolean isAccepted;
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name="applicant_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="market_post_id")
    private MarketPost marketPost;

    public void updateAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public static Apply toEntity(String introduction, Profile Profile, MarketPost marketPost){
        return Apply.builder()
                .content(introduction)
                .profile(Profile)
                .status(Status.WAITING)
                .isAccepted(Boolean.FALSE)
                .marketPost(marketPost).build();
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
