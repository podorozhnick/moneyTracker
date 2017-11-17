package com.podorozhnick.moneytracker.pojo.search.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum  CategorySortField implements SortField {

    NAME("name"), TYPE("type");

    @Getter
    private String field;

}
