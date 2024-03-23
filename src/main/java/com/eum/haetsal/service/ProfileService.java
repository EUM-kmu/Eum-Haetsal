package com.eum.haetsal.service;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.FileDto;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.common.KoreaLocalDateTime;
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
import java.util.Date;

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
    public ProfileResponseDTO.ProfileResponseWithToken create(ProfileRequestDTO.CreateProfile createProfile, Long userId, MultipartFile multipartFile) throws ParseException {
        User getUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("invalid userId"));
        if (profileRepository.existsByUser(getUser)) throw new IllegalArgumentException("이미 프로필이 있는 회원");

        validateNickname(createProfile.getNickname());
        FileDto fileDto = fileService.uploadFile(multipartFile, "profile");
        Profile profile = Profile.toEntity(createProfile,getUser,fileDto.getUploadFileUrl(),fileDto.getUploadFileName());
        Profile savedProfile = profileRepository.save(profile);

        ProfileResponseDTO.ProfileResponseWithToken createProfileResponse = ProfileResponseDTO.toProfileToken(savedProfile);
        return createProfileResponse;

    }
//    @Transactional
//    public ProfileResponseDTO.ProfileResponse create(ProfileRequestDTO.CreateProfile createProfile, Long userId) {
//        User getUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("invalid userId"));
//        if (profileRepository.existsByUser(getUser)) throw new IllegalArgumentException("이미 프로필이 있는 회원");
//
//        validateNickname(createProfile.getNickname());
////        FileDto fileDto = fileService.uploadFile(multipartFile, "profile");
//        Profile profile = Profile.toEntity(createProfile,getUser,"","");
//        Profile savedProfile = profileRepository.save(profile);
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
    public APIResponse<ProfileResponseDTO.ProfileResponse> getMyProfile(Long userId) {
        User getUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid argument"));
        Profile getProfile = profileRepository.findByUser(getUser).orElseThrow(() -> new NullPointerException("프로필이 없습니다"));
        ProfileResponseDTO.ProfileResponse profileResponseDTO = ProfileResponseDTO.toProfileResponse(getProfile );
        return APIResponse.of(SuccessCode.SELECT_SUCCESS, profileResponseDTO);
    }

    /**
     * 닉네임 중복 확인
     * @param nickname
     */
    private void validateNickname(String nickname){
        if(profileRepository.existsByNickname(nickname)) throw new IllegalArgumentException("이미 있는 닉네임");
    }

    /**
     * 프로필 수정
     * @param updateProfile
     * @param userId
     * @return
     */
    @Transactional
    public APIResponse updateMyProfile(ProfileRequestDTO.UpdateProfile updateProfile,Long userId,MultipartFile multipartFile) {
        Profile getProfile = findByUser(userId);
        validateNickname(updateProfile.getNickname());

        fileService.deleteFile("profile",getProfile.getFileName());
        FileDto fileDto = fileService.uploadFile(multipartFile,"profile");

        getProfile.updateNickName(updateProfile.getNickname());
        getProfile.updateProfileImage(fileDto.getUploadFileUrl());
        getProfile.updateFileName(fileDto.getUploadFileName());

        profileRepository.save(getProfile);
        return APIResponse.of(SuccessCode.UPDATE_SUCCESS);
    }
    public Profile findByUser(Long userId){
        User getUser = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("Invalid userId"));
        Profile getProfile = profileRepository.findByUser(getUser).orElseThrow(() -> new NullPointerException("None profile User"));
        return getProfile;
    }


}
