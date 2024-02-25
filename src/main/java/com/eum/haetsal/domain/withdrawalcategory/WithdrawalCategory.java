package com.eum.haetsal.domain.withdrawalcategory;

import com.eum.haetsal.domain.withdrawaluser.WithdrawalUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WithdrawalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long withdrawalCategoryId;

    @Column
    private String content;

    @OneToMany(mappedBy = "withdrawalCategory")
    private List<WithdrawalUser> withdrawalUsers = new ArrayList<>();
}
