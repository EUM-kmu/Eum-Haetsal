package com.eum.haetsal.controller.DTO.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ChatRequestDTO {
    @Getter
    @Setter
    public static class PostIdList{
        private List<String> postIdList;
    }
    @Getter
    @Setter
    public static class UserIdList{
        private List<String> userIdList;
    }
}
