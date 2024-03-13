package com.eum.haetsal.domain.marketpost;

import com.eum.haetsal.common.BaseTimeEntity;
import com.eum.haetsal.controller.DTO.request.MarketPostRequestDTO;
import com.eum.haetsal.controller.DTO.request.enums.MarketType;
import com.eum.haetsal.domain.apply.Apply;
import com.eum.haetsal.domain.category.MarketCategory;
import com.eum.haetsal.domain.profile.Profile;
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
    private Long dealId;

    @CreationTimestamp
    @Column
    private LocalDateTime pullUpDate;

    @Column
    @Enumerated(EnumType.STRING)
    private MarketType marketType;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @Enumerated(EnumType.STRING)
    private Slot slot;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="category_id")
    private MarketCategory marketCategory;

    @OneToMany(mappedBy = "marketPost", orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();


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
    public void updateSlot(Slot slot) {this.slot = slot;}
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

    public static MarketPost toEntity(MarketPostRequestDTO.MarketCreate marketCreate, Long pay, Profile profile, MarketCategory marketCategory) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.KOREAN);
        return MarketPost.builder()
                .title(marketCreate.getTitle())
                .content(marketCreate.getContent())
                .startDate(simpleDateFormat.parse(marketCreate.getStartTime()))
                .slot(marketCreate.getSlot())
                .pay(pay)
                .isDeleted(false)
                .location(marketCreate.getLocation())
                .volunteerTime(marketCreate.getVolunteerTime())
                .marketType(marketCreate.getMarketType())
                .maxNumOfPeople(marketCreate.getMaxNumOfPeople())
                .status(Status.RECRUITING)
                .profile(profile)
                .marketCategory(marketCategory)
                .build();
    }
}
