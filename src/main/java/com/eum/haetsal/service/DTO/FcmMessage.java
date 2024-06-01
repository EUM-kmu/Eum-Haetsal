package com.eum.haetsal.service.DTO;

import com.eum.haetsal.service.DTO.enums.MessageForm;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FcmMessage {
    private String title;
    private String message;
    @Builder
    public FcmMessage(final MessageForm messageForm){
        this.title = messageForm.getTitle();
        this.message = messageForm.getMessage();
    }
    @Builder
    public FcmMessage(final MessageForm messageForm,String message){
        this.title = messageForm.getTitle();
        this.message = message;
    }
    @Builder
    public FcmMessage(String title,String message){
        this.title = title;
        this.message = message;
    }
    public static FcmMessage of(final MessageForm messageForm) {return new FcmMessage(messageForm);}
    public static FcmMessage of(final MessageForm messageForm,String message) {return new FcmMessage(messageForm,message);}
    public static FcmMessage of(final String title,String message) {return new FcmMessage(title, message);}



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
