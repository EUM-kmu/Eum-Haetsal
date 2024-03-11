package com.eum.haetsal.domain.user;

import com.eum.haetsal.common.BaseTimeEntity;
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
public class User extends BaseTimeEntity {
    @Id
    private Long userId;

    @Column(length = 50)
    private String accountNumber;

    public static User toEntity(Long userId) {
        return User.builder().userId(userId).build();
    }
}