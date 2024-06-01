package com.eum.haetsal.domain.notification;

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
public class MessageLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @Column
    private String token;
    private String title;
    private String message;

    @ManyToOne
    private User user;


}