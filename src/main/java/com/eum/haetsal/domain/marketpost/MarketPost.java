package com.eum.haetsal.domain.marketpost;

import com.eum.haetsal.common.BaseTimeEntity;
import com.eum.haetsal.controller.DTO.request.MarketPostRequestDTO;
import com.eum.haetsal.controller.DTO.request.enums.MarketType;
import com.eum.haetsal.domain.apply.Apply;
import com.eum.haetsal.domain.category.MarketCategory;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.report.Report;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MarketPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_post_id")
    private Long marketPostId;

    @Column
    private String title;
    private String content;
    private Long pay;
    private String location;
    private int volunteerTime;
    private int maxNumOfPeople;
    private int currentAcceptedPeople;
    private Date startDate;
    private boolean isDeleted;
    private Long viewsCount;
    private Long dealId;
    private Long reportedCount;

    @CreationTimestamp
    @Column
    private LocalDateTime pullUpDate;

//    @Column
//    @Enumerated(EnumType.STRING)
    private MarketType marketType;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="category_id")
    private MarketCategory marketCategory;

    @OneToMany(mappedBy = "marketPost", orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "marketPost")
    private List<Report> reports  = new ArrayList<>();


    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.content = contents;
    }
    public void addCurrentAcceptedPeople(){
        this.currentAcceptedPeople += 1;
    }
    public void subCurrentAcceptedPeople(){
        this.currentAcceptedPeople -= 1;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
    public void updateStartDate(Date startDate) {this.startDate = startDate;}
    public  void updateLocation(String location) {this.location = location;}

    public void updateVolunteerTime(int volunteerTime) {
        this.volunteerTime = volunteerTime;
    }

    public void updateMaxNumOfPeople(int maxNumOfPeople) {
        this.maxNumOfPeople = maxNumOfPeople;
    }

    public void updatePay(Long pay) {
        this.pay = pay;
    }

    public void updateDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void addViewsCount() {
        this.viewsCount += 1;
    }

    public void increaseReportedCount(Long userId) {
        this.reports.forEach(report -> {
                    if(report.getProfile().getUser().getUserId().equals(userId)){
                        throw new IllegalArgumentException("이미 신고한 게시물입니다.");
                    }
                });
        
        this.reportedCount += 1;
    }


    public static MarketPost toEntity(MarketCategory marketCategory,MarketPostRequestDTO.MarketCreate marketCreate, Long pay, Profile profile) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.KOREAN);
        return MarketPost.builder()
                .title(marketCreate.getTitle())
                .content(marketCreate.getContent())
                .startDate(simpleDateFormat.parse(marketCreate.getStartDate()))
                .pay(pay)
                .isDeleted(false)
                .location(marketCreate.getLocation())
                .volunteerTime(marketCreate.getVolunteerTime())
                .viewsCount(0L)
                .marketType(MarketType.REQUEST_HELP)
                .marketCategory(marketCategory)
                .maxNumOfPeople(marketCreate.getMaxNumOfPeople())
                .status(Status.RECRUITING)
                .profile(profile)
                .reportedCount(0L)
                .build();
    }
}
