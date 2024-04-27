package com.eum.haetsal.service;

import com.eum.haetsal.common.DTO.APIResponse;
import com.eum.haetsal.common.DTO.enums.SuccessCode;
import com.eum.haetsal.controller.DTO.request.ApplyRequestDTO;
import com.eum.haetsal.controller.DTO.response.ApplyResponseDTO;
import com.eum.haetsal.domain.apply.Apply;
import com.eum.haetsal.domain.apply.ApplyRepository;
import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.marketpost.MarketPostRepository;
import com.eum.haetsal.domain.marketpost.Status;
import com.eum.haetsal.domain.profile.Profile;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.eum.haetsal.domain.apply.Status.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final MarketPostRepository marketPostRepository;
    private final ApplyResponseDTO applyResponseDTO;
    private final BankService bankService;

    /**
     * 지원하기
     * @param postId
     * @param applyRequest
     * @param profile
     * @return
     */
    public APIResponse doApply(Long postId, ApplyRequestDTO.Apply applyRequest, Profile profile) {
        MarketPost getMarketPost = marketPostRepository.findById(postId).orElseThrow(() -> new NullPointerException("Invalid postId"));

        if(getMarketPost.isDeleted()) throw new IllegalArgumentException("Deleted post");
        if(getMarketPost.getProfile() == profile) throw new IllegalArgumentException("자기 게시글에는 신청할수 없습니다");
//        if(getMarketPost.getMarketType()== MarketType.PROVIDE_HELP && getMarketPost.getPay() > getUser.getUserBankAccount().getBalance())
//            throw new IllegalArgumentException("잔액보다 큰 요구 햇살"); //지원하려는 게시글의 요구 햇살이 내 잔액보다 클때
        if(getMarketPost.getCurrentAcceptedPeople() >= getMarketPost.getMaxNumOfPeople()) throw new RuntimeException("최대 신청자 수를 넘었습니다");
        if (applyRepository.existsByProfileAndMarketPost(profile, getMarketPost)) throw new IllegalArgumentException("이미 신청했음");
        Apply apply = Apply.toEntity(applyRequest.getIntroduction(), profile, getMarketPost);
        applyRepository.save(apply);
        marketPostRepository.save(getMarketPost);
        return APIResponse.of(SuccessCode.INSERT_SUCCESS);
    }

    /**
     * 게시글 별 지원리스트 조회
     * @param postId 조회할 게시글 id
     * @return 지원리스트
     */
    public APIResponse<List<ApplyResponseDTO.ApplyListResponse>> getApplyList(Long postId) {
        MarketPost getMarketPost = marketPostRepository.findById(postId).orElseThrow(() -> new NullPointerException("Invalid id"));
        List<ApplyResponseDTO.ApplyListResponse> getAllApplicants = findByMarketPosts(getMarketPost);
        return APIResponse.of(SuccessCode.SELECT_SUCCESS, getAllApplicants);
    }

    /**
     * 햇터 게시글로 찾은 지원리스트 DTO로 변환
     * @param marketPost
     * @return 지원 리스트 DTO
     */
    private List<ApplyResponseDTO.ApplyListResponse> findByMarketPosts(MarketPost marketPost){
        List<ApplyResponseDTO.ApplyListResponse> applyListResponses = new ArrayList<>();
        List<Apply> applies = applyRepository.findByMarketPostOrderByCreateDateDesc(marketPost).orElse(Collections.emptyList());
        for(Apply apply : applies){
//                해당 신청과 매핑되는 신청자 프로필 조회
                Profile getApplicantProfile = apply.getProfile();
                ApplyResponseDTO.ApplyListResponse singleApplyResponseDTO = applyResponseDTO.newApplyListResponse(marketPost, getApplicantProfile,apply);
                applyListResponses.add(singleApplyResponseDTO);
        }
        return applyListResponses;
    }

    /**
     * 지원 수락
     * @param applyIds 수락할 지원 id들
     * @param profile
     * @return
     */
    @Transactional
    public APIResponse accept(Long postId, Long dealId, List<Long> applyIds, Profile profile) {

        String[] receiverAccountNumbers = applyIds.stream().map(
            applyId -> applyRepository.findById(applyId).orElseThrow(() ->
                new NullPointerException("invalid applyId")).getProfile().getUser().getAccountNumber()).toArray(String[]::new);

        String password = profile.getUser().getAccountPassword();
        MarketPost marketPost = marketPostRepository.findById(postId).orElseThrow(() -> new NullPointerException("invalid postId"));

        applyIds.forEach(applyId -> {
            Apply getApply = applyRepository.findById(applyId).orElseThrow(() -> new NullPointerException("invalid applyId"));

            if (getApply.getMarketPost().getProfile() != profile) throw new IllegalArgumentException("해당 게시글에 대한 권한이 없다");
            if(!marketPost.getStatus().equals(Status.RECRUITING)) throw new IllegalArgumentException("이미 모집완료 된 게시글입니다");
            if( getApply.getStatus().equals(TRADING_CANCEL)) throw new IllegalArgumentException("과거 거래 취소를 했던 사람입니다");


            getApply.updateAccepted(true); //수락
            getApply.updateStatus(TRADING); //지원 상태 변경
            marketPost.addCurrentAcceptedPeople(); //게시글에 반영

            applyRepository.save(getApply);
        });

        marketPost.updateStatus(Status.RECRUITMENT_COMPLETED);
        marketPostRepository.save(marketPost);

        bankService.successDeal(dealId, receiverAccountNumbers ,password);
        return APIResponse.of(SuccessCode.UPDATE_SUCCESS, "선정성공, 채팅방 개설 완료");
    }

    /**
     * 지원 취소
     * @param postId

     * @param applyId
     * @param profile 신청자
     * @return
     */
    public APIResponse unApply(Long postId, Long applyId, Profile profile, Profile deleteProfile) {
        Apply getApply = applyRepository.findById(applyId).orElseThrow(() -> new NullPointerException("invalid applyId"));
        MarketPost post = marketPostRepository.findById(postId).orElseThrow(() -> new NullPointerException("invalid postId"));
        Profile hostProfile = post.getProfile();

        if(!Objects.equals(getApply.getMarketPost().getMarketPostId(), postId)) throw new IllegalArgumentException("invalid postId");
//        if(getApply.getIsAccepted()) throw new IllegalArgumentException("이미 선정되서 취소할 수 없습니다");
        if (!post.getStatus().equals(Status.RECRUITING)) throw new IllegalArgumentException("모집중인 게시글이 아닙니다");

        /*
        호스트가 취소 or 지원자가 취소 가 아닐 경우 권한이 없다.
         */
        if( !hostProfile.equals(profile) && !deleteProfile.equals(profile)) {
            throw new IllegalArgumentException("취소할수있는 권한이 없습니다");
        }
        if(getApply.getStatus() == TRADING) {
            getApply.setIsAccepted(false);
            getApply.setStatus(TRADING_CANCEL);
            applyRepository.save(getApply);
        }else if (getApply.getStatus() == WAITING){
            applyRepository.delete(getApply);
        }
        // 지원자의 지원 상태를 변경
        post.setCurrentAcceptedPeople(post.getCurrentAcceptedPeople() - 1);
        marketPostRepository.save(post);

        return APIResponse.of(SuccessCode.DELETE_SUCCESS);
    }



    /**
     * 차단 할 때 지원 리스트 반영
     * @param blocker 차단하는 유저
     * @param blocked 처단 당한 유저
     */
    public void blockedAction(Profile blocker, Profile blocked) {
        List<Apply> applies = new ArrayList<>();
        List<MarketPost> myPosts = marketPostRepository.findByProfileAndIsDeletedFalse(blocker).orElse(Collections.emptyList()); //차단한 유저의 게시글(나)
        List<Apply> tradingList = applyRepository.findTradingAppliesByApplicantAndPostIn(blocked,myPosts).orElse(Collections.emptyList()); // 개사굴애소 자원리스트 조회
        applies.addAll(tradingList);

        List<MarketPost> opponentsPosts = marketPostRepository.findByProfileAndIsDeletedFalse(blocked).orElse(Collections.emptyList()); //차단된 유저의 게시글(상대)
        List<Apply> tradedList = applyRepository.findTradingAppliesByApplicantAndPostIn(blocker,opponentsPosts).orElse(Collections.emptyList());
        applies.addAll(tradedList);

        cancelByType(applies); //지원 상태 타입별 처리
    }

    /**
     * 탈퇴 유저의 지원 리스트 처리
     * @param profile
     */
    public void withdrawalApply(Profile profile) {
        List<Apply> applies = applyRepository.findByProfile(profile).orElse(Collections.emptyList()); //탈퇴한 유저의 지원 리트 조회
        cancelByType(applies);
    }

    /**
     * 취소 DB에 반영
     * @param getApply
     */
    private void cancel(Apply getApply){
        getApply.updateStatus(TRADING_CANCEL); //상태변경
        getApply.updateAccepted(false);
        applyRepository.save(getApply);

        getApply.getMarketPost().subCurrentAcceptedPeople(); //게시글 반영
        getApply.getMarketPost().updateStatus(Status.RECRUITING); //모집중으로 변경
        marketPostRepository.save(getApply.getMarketPost());
    }

    /**
     * 선정 전, 선정 후 타입별 지원 처리
     * @param applies
     */
    private void cancelByType(List<Apply> applies){
        for (Apply apply:applies) {
            if (apply.getStatus() == com.eum.haetsal.domain.apply.Status.WAITING) {
                applyRepository.delete(apply);
            } else if (apply.getStatus() == TRADING) {
                cancel(apply);
            }
        }
    }
}
