package com.eum.haetsal.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FcmMessage {
    private static String TESTTITLE = "이건 테스트";
    private static String TESTMESSAGE = "테스트임";
    @AllArgsConstructor
    @Getter
    public static class Test{
        private String title;
        private String message;
        private String token;

        public Test(String token) {
            this.token = token;
            this.title = TESTTITLE;
            this.message = TESTMESSAGE;
        }
    }
}
