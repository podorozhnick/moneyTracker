package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.pojo.search.enums.SortField;
import com.podorozhnick.moneytracker.pojo.search.enums.SortType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class SortFilter {

    public abstract SortField getSortField();

    @NotNull
    private SortType sortType;

}
