package com.eum.haetsal.controller.DTO.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TimeBankRequestDTO {
    @Getter
    @Setter
    public static class AccountNumberList{
        private List<String> accountNumberList;
    }
}
