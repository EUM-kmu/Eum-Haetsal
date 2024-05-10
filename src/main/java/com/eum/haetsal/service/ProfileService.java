package com.eum.haetsal.service;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.FileDto;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.ProfileRequestDTO;
import com.eum.haetsal.controller.DTO.response.ProfileResponseDTO;
import com.eum.haetsal.domain.profile.Profile;
import com.eum.haetsal.domain.profile.ProfileRepository;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    private final FileService fileService;
    /**
     * 프로필 생성
     * @param createProfile
     * @param userId
     * @return
     */
    @Transactional
    public ProfileResponseDTO.ProfileResponseWithToken create(ProfileRequestDTO.CreateProfile createProfile, Long userId) throws ParseException {
        User getUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("invalid userId"));
        if (profileRepository.existsByUser(getUser)) throw new IllegalArgumentException("이미 프로필이 있는 회원");

        FileDto fileDto = fileService.uploadFileFromBase64(createProfile.getFileByte(),"profile", "profile");
        Profile profile = Profile.toEntity(createProfile,getUser,fileDto);
        Profile savedProfile = profileRepository.save(profile);

        ProfileResponseDTO.ProfileResponseWithToken createProfileResponse = ProfileResponseDTO.toProfileToken(savedProfile);
        return createProfileResponse;

    }
//    @Transactional
//    public ProfileResponseDTO.ProfileResponse createT(ProfileRequestDTO.CreateProfile createProfile, Long userId) throws ParseException {
//        User getUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("invalid userId"));
//        if (profileRepository.existsByUser(getUser)) throw new IllegalArgumentException("이미 프로필이 있는 회원");
//
//        validateNickname(createProfile.getNickName());
////        FileDto fileDto = fileService.uploadFile(multipartFile, "profile");
////        Profile profile = Profile.toEntity(createProfile,getUser,FileDto);
////        Profile savedProfile = profileRepository.save(profile);
//
//        ProfileResponseDTO.ProfileResponse createProfileResponse = ProfileResponseDTO.toProfileResponse(savedProfile);
//        return createProfileResponse;
//
//    }

    /**
     * 프로필 조회
     * @param userId
     * @return
     */
    public ProfileResponseDTO.ProfileResponse getProfile(Profile profile) {
        ProfileResponseDTO.ProfileResponse profileResponseDTO = ProfileResponseDTO.toProfileResponse(profile );
        return  profileResponseDTO;
    }

    /**
     * 프로필 수정
     * @param updateProfile
     * @param userId
     * @return
     */
    @Transactional
    public Profile updateMyProfile(ProfileRequestDTO.UpdateProfile updateProfile,Long userId) {
        Profile getProfile = findByUser(userId);
        if(updateProfile.getFileByte() != null) {
            fileService.deleteFile("profile",getProfile.getFileName());
            FileDto fileDto = fileService.uploadFileFromBase64(updateProfile.getFileByte(),"profile", "profile");
            getProfile.updateProfileImage(fileDto.getUploadFileUrl());
            getProfile.updateFileName(fileDto.getUploadFileName());
        }
        getProfile.updateNickName(updateProfile.getNickName());
        getProfile.updateAddress(updateProfile.getAddress());
        getProfile.updateBirth(updateProfile.getBirth());
        getProfile.updateGender(updateProfile.getGender());

        return profileRepository.save(getProfile);
    }
    public Profile findByUser(Long userId){
        User getUser = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("Invalid userId"));
        Profile getProfile = profileRepository.findByUser(getUser).orElseThrow(() -> new NullPointerException("None profile User"));
        return getProfile;
    }
    @Transactional
    public void removePrivacy(Profile profile){
        profile.setAddress("");
        profile.setName("");
        profile.setNickname("알수없음");
        fileService.deleteFile("profile",profile.getFileName());
        profile.setFileName("");
        profile.setProfileImage("");
        profile.setDeleted(false);
        profileRepository.save(profile);
    }



}
