package com.eum.haetsal.domain.user;

import com.eum.haetsal.common.BaseTimeEntity;
import com.eum.haetsal.domain.report.Report;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(length = 50)
    private String accountNumber;

    @Column
    private String accountPassword;

    @OneToMany(mappedBy = "user")
    private List<Report> reports;

    public static User toEntity(Long userId, String password) {
        return User.builder().userId(userId).accountPassword(password).build();
    }
}