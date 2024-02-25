package com.eum.haetsal.domain.user;

import com.eum.haetsal.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseTimeEntity {
    @Id
    private Long userId;

    public static User toEntity(Long userId) {
        return User.builder().userId(userId).build();
    }
}