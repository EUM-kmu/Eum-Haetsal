package com.eum.haetsal.domain.withdrawaluser;


import com.eum.haetsal.common.BaseTimeEntity;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.withdrawalcategory.WithdrawalCategory;
import com.eum.haetsal.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WithdrawalUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long withdrawalUserId;

    @Column
    private String reason;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "withdrawal_category_id")
    private WithdrawalCategory withdrawalCategory;

    public static WithdrawalUser toEntity(Profile profile, String reason, WithdrawalCategory withdrawalCategory){
        return WithdrawalUser.builder()
                .profile(profile)
                .reason(reason)
                .withdrawalCategory(withdrawalCategory).build();
    }

}
