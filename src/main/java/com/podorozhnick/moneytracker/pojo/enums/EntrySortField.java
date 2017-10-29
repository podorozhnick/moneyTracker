package com.podorozhnick.moneytracker.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EntrySortField {

    AMOUNT("amount"), DATE("date"), CATEGORY("category");

    @Getter
    private String field;

}
