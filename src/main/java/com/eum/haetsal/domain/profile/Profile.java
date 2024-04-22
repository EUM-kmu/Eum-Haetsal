package com.eum.haetsal.domain.profile;


import com.eum.haetsal.common.BaseTimeEntity;
import com.eum.haetsal.common.DTO.FileDto;
import com.eum.haetsal.common.KoreaLocalDateTime;
import com.eum.haetsal.controller.DTO.request.ProfileRequestDTO;
import com.eum.haetsal.domain.block.Block;
import com.eum.haetsal.domain.apply.Apply;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.report.Report;
import com.eum.haetsal.domain.withdrawaluser.WithdrawalUser;
import com.eum.haetsal.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Profile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column
    private String name;
    private String nickname;
    private LocalDate birth;
    private String gender;
    private String profileImage;
    private String address;
    private String fileName;
    private int dealCount;


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

    @OneToMany(mappedBy = "profile")
    private List<Report> reports;

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




    public static Profile toEntity(ProfileRequestDTO.CreateProfile createProfile, User user, FileDto fileDto) throws ParseException {
        LocalDate birthDate = KoreaLocalDateTime.stringToLocalDateTime(createProfile.getBirth());
        return Profile.builder()
                .nickname(createProfile.getNickName())
                .user(user)
                .name(createProfile.getName())
                .birth(birthDate)
                .gender(createProfile.getGender())
                .fileName(fileDto.getUploadFileName())
                .profileImage(fileDto.getUploadFileUrl())
                .address(createProfile.getAddress())
                .build();
    }

}
