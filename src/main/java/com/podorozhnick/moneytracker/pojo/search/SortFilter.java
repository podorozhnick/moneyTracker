package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.pojo.search.enums.SortField;
import com.podorozhnick.moneytracker.pojo.search.enums.SortType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SortFilter {

    public abstract SortField getSortField();

    private SortType sortType;

}
