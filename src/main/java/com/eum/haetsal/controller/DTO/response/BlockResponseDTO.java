package com.eum.haetsal.controller.DTO.response;

import com.eum.haetsal.client.DTO.ChatResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BlockResponseDTO {

    @Getter
    @Setter
    public static class TotalChatInfo{
        @Schema(description = "차단한 사람의 userId")
        private Long blockedUserId;
        private List<ChatResponse> chatInfos;

        public TotalChatInfo(Long blockedUserId, List<ChatResponse> chatInfos) {
            this.blockedUserId = blockedUserId;
            this.chatInfos = chatInfos;
        }
    }

    @Getter
    @Setter
    public static class ChatInfo{
        private String roomId;
        private int postId;

        public ChatInfo(ChatResponse chatResponse) {
            this.roomId = chatResponse.getRoomId();
            this.postId = chatResponse.getPostId();
        }
    }



}
