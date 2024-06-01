package com.eum.haetsal.domain.fcmtoken;


import com.eum.haetsal.common.BaseTimeEntity;
import com.eum.haetsal.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FcmToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fcmTokenId;

    @Column
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
