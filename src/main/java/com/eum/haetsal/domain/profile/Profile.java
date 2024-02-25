package com.eum.haetsal.domain.profile;


import com.eum.haetsal.common.BaseTimeEntity;
import com.eum.haetsal.controller.DTO.request.ProfileRequestDTO;
import com.eum.haetsal.domain.block.Block;
import com.eum.haetsal.domain.apply.Apply;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.withdrawaluser.WithdrawalUser;
import com.eum.haetsal.domain.user.User;
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
public class Profile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column
    private String name;
    private String nickname;
    private String address;
    private String profileImage;
    private String fileName;

    @OneToMany(mappedBy = "blocker")
    private List<Block> blockers = new ArrayList<>();

    @OneToMany(mappedBy = "blocked")
    private List<Block> blockedUsers = new ArrayList<>();




    @OneToMany(mappedBy = "profile", orphanRemoval = true)
    private List<MarketPost> marketPosts = new ArrayList<>();





    @OneToMany(mappedBy = "profile", orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();


    @OneToOne(mappedBy = "profile", orphanRemoval = true)
    private WithdrawalUser withdrawalUser;





    @OneToOne
    @JoinColumn(name="user_id")
    private User user;


    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

    public void updateFileName(String fileName) {
        this.fileName = fileName;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public void updateAddress(String address) {
        this.address = address;
    }




    public static Profile toEntity(ProfileRequestDTO.CreateProfile createProfile, User user, String profileImage, String fileName){
        return Profile.builder()
                .nickname(createProfile.getNickname())
                .user(user)
                .profileImage(profileImage)
                .build();
    }

}
