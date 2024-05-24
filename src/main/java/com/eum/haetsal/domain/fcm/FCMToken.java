package com.eum.haetsal.domain.fcm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FCMToken {
    @Id
    private Long userId;
    @Column
    private String token;
}
