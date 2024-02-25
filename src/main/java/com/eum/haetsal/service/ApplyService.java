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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final MarketPostRepository marketPostRepository;
    private final ApplyResponseDTO applyResponseDTO;


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
     * 지언 수락
     * @param applyIds 수락할 지원 id들
     * @param profile
     * @return
     */
    public APIResponse accept(List<Long> applyIds, Profile profile) {
        applyIds.stream().forEach(applyId -> {
            Apply getApply = applyRepository.findById(applyId).orElseThrow(() -> new NullPointerException("invalid applyId"));

            if (getApply.getMarketPost().getProfile() != profile) throw new IllegalArgumentException("해당 게시글에 대한 권한이 없다");
            if(getApply.getIsAccepted() == true || getApply.getStatus() == com.eum.haetsal.domain.apply.Status.TRADING_CANCEL) throw new IllegalArgumentException("이미 선정했더나 과거 거래 취소를 했던 사람입니다");

            MarketPost marketPost = getApply.getMarketPost();
            getApply.updateAccepted(true); //수락
            getApply.updateStatus(com.eum.haetsal.domain.apply.Status.TRADING); //지원 상태 변경
            marketPost.addCurrentAcceptedPeople(); //게시글에 반영
            if(marketPost.getCurrentAcceptedPeople() == marketPost.getMaxNumOfPeople()) {
                marketPost.updateStatus(Status.RECRUITMENT_COMPLETED); //정원이 다차면 완료 처리
            }

            marketPostRepository.save(marketPost);
            applyRepository.save(getApply);

//            try {
//                chatService.createChatRoomWithFireStore(applyId); //채팅방 생성
//            } catch (ExecutionException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        });
        return APIResponse.of(SuccessCode.UPDATE_SUCCESS, "선정성공, 채팅방 개설 완료");
    }

    /**
     * 선정 전 지원 취소
     * @param postId
     * @param applyId
     * @param profile
     * @return
     */
    public APIResponse unApply(Long postId, Long applyId, Profile profile) {
        Apply getApply = applyRepository.findById(applyId).orElseThrow(() -> new NullPointerException("invalid applyId"));

        if(getApply.getMarketPost().getMarketPostId() != postId) throw new IllegalArgumentException("invalid postId");
        if(getApply.getProfile() != profile) throw new IllegalArgumentException("신청 취소할 권한이 없습니다");
        if(getApply.getIsAccepted() == true) throw new IllegalArgumentException("이미 선정되서 취소할 수 없습니다");

        applyRepository.delete(getApply);
        return APIResponse.of(SuccessCode.DELETE_SUCCESS);

    }

    /**
     * 선정 후 활동 파기
     * @param postId
     * @param chatId
     * @param userId
     * @return
     */
    public void cancel(Long postId, Long chatId, Long userId) {
//        Users getUser = usersRepository.findById(userId). orElseThrow(() -> new NullPointerException("Invalid email"));
//        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(() -> new IllegalArgumentException("해딩 채팅방이 없습니다"));
//        if(chatRoom.getMarketPost().getMarketPostId() != postId) throw new IllegalArgumentException("invalid postId");
//
//        MarketPost getMarketPost = chatRoom.getMarketPost();
//        Apply getApply = applyRepository.findByProfileAndMarketPost(chatRoom.getApplicant(), getMarketPost).orElseThrow(()->new IllegalArgumentException("신청한 이력이 없는데 채팅방이 있다"));
//
//        if(!(getUser == chatRoom.getApplicant() || getUser == chatRoom.getPostWriter() )) throw new IllegalArgumentException("활동 파기할수있는 권한이 없습니다");
////        채팅 금지 처리
//        chatRoom.upDateBlocked(true);
//        cancel(getApply);
//        return APIResponse.of(SuccessCode.DELETE_SUCCESS);

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
        getApply.updateStatus(com.eum.haetsal.domain.apply.Status.TRADING_CANCEL); //상태변경
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
            } else if (apply.getStatus() == com.eum.haetsal.domain.apply.Status.TRADING) {
                cancel(apply);
            }
        }
    }
}