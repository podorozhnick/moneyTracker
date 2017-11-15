package com.podorozhnick.moneytracker.pojo.search.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EntrySortField implements SortField {

    AMOUNT("amount"), DATE("date"), CATEGORY("category");

    @Getter
    private String field;

    @Override
    public String getFieldName() {
        return field;
    }
}
