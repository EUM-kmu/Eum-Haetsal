package com.eum.haetsal.controller.DTO.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class BankAccountResponseDTO {
    private static String SYSTEM_PHOTO_URL="https://kr.object.ncloudstorage.com/k-eum/simpleCharaterAsset/system.png";
    @Getter
    @Setter
    @Builder
    public static class CheckNickName{
        private String receiverNickName;
        private String receiverCardName;
        private Long myBalance;

    }

    @Getter
    @Setter
    @Builder
    private static class OpponentInfo{
        private String nickName;
        private String cardName;
        private String profileImage;
    }


    @Getter
    public static class History {
        private String transactionType;
        private OpponentInfo opponentInfo;
        private Long myCurrentBalance;
        private Long amount;
        private String createdTime;


        public History(String transactionType, OpponentInfo opponentInfo, Long myCurrentBalance, Long amount, String createdTime) {
            this.transactionType = transactionType;
            this.opponentInfo = opponentInfo;
            this.myCurrentBalance = myCurrentBalance;
            this.amount = amount;
            this.createdTime = createdTime;
        }
    }
    @Builder
    @Getter
    @Setter
    public static class HistoryWithInfo{
        private String cardName;
        private Long balance;
        private List<History> histories;
    }
//    public History newHistory(){
//        LocalDateTime utcDateTime = LocalDateTime.parse(bankAccountTransaction.getCreateDate().toString(), DateTimeFormatter.ISO_DATE_TIME);
//        ZonedDateTime koreaZonedDateTime = utcDateTime.atZone(ZoneId.of("Asia/Seoul"));
//        // 한국 시간대로 포맷팅
//        String formattedDateTime = koreaZonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"));
//        if(bankAccountTransaction.getTransactionType() == TransactionType.WITHDRAW){
//            String receiverNickName = bankAccountTransaction.getReceiverBankAccount().getUser().getProfile().getNickname();
//            String profileImage =  bankAccountTransaction.getReceiverBankAccount().getUser().getProfile().getProfileImage();
//            String cardName = bankAccountTransaction.getReceiverBankAccount().getAccountName();
//            OpponentInfo receiverInfo = OpponentInfo.builder().nickName(receiverNickName).profileImage(profileImage).cardName(cardName).build();
//            return new History(TransactionType.WITHDRAW, receiverInfo, bankAccountTransaction.getMyCurrentBalance(), bankAccountTransaction.getAmount(), formattedDateTime);
//        }
//        String senderNickName = (bankAccountTransaction.getSenderBankAccount() == null) ?  bankAccountTransaction.getBranchBankAccount().getAccountName():bankAccountTransaction.getSenderBankAccount().getUser().getProfile().getNickname();
//        String cardName = (bankAccountTransaction.getSenderBankAccount() == null) ?  "":bankAccountTransaction.getSenderBankAccount().getAccountName();
//        String profileImage = (bankAccountTransaction.getSenderBankAccount() == null) ? SYSTEM_PHOTO_URL : bankAccountTransaction.getSenderBankAccount().getUser().getProfile().getProfileImage();
//        OpponentInfo receiverInfo = OpponentInfo.builder().nickName(senderNickName).cardName(cardName).profileImage(profileImage).build();
//        return new History(TransactionType.DEPOSIT, receiverInfo, bankAccountTransaction.getMyCurrentBalance(), bankAccountTransaction.getAmount(), formattedDateTime);

//    }
    @Builder
    @Getter
    @Setter
    public static class AccountInfo {
        private String cardName;
        private Long balance;
    }
}
