/**
 * @file Constant.java
 * 상수값 정의
 */
package com.eum.haetsal.common;

public final class Constant {
    /**
     * 계좌 번호 길이.
     */
    public static final int ACCOUNT_NUMBER_LENGTH = 12;

    /**
     * 자유 송금 타입.
     */
    public static final String FREE_TYPE = "free";

    /**
     * 일괄 송금 타입.
     */
    public static final String BATCH_TYPE = "batch";

    /**
     * 거래 생성 후 거래 성사 전 (수신계좌가 안엮인 상태).
     */
    public static final String BEFORE_DEAL = "before_deal";

    /**
     * 거래 성사 후 (수신계좌가 엮인 상태).
     */
    public static final String AFTER_DEAL = "after_deal";

    /**
     * 거래 취소 됨.
     */
    public static final String CANCEL_DEAL = "cancel_deal";

    /**
     * 거래 수행 됨.
     */
    public static final String DONE_DEAL = "done_deal";

    /**
     * 금약 증가
     */
    public static final String INCREASE = "increase";

    /**
     * 금액 감소
     */
    public static final String DECREASE = "decrease";

    private Constant() {
    }

}
