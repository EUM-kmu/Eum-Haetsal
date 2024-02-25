package com.eum.haetsal.domain.block;

import com.eum.haetsal.domain.profile.Profile;
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
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    @ManyToOne
    @JoinColumn(name = "blocker_id")
    private Profile blocker;

    @ManyToOne
    @JoinColumn(name = "blocked_id")
    private Profile blocked;


    public static Block toEntity(Profile blocker, Profile blocked) {
        return Block.builder().blocker(blocker).blocked(blocked).build();
    }
}
