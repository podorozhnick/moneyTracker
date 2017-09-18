package com.podorozhnick.moneytracker.pojo;

import com.podorozhnick.moneytracker.pojo.enums.EntrySortField;
import com.podorozhnick.moneytracker.pojo.enums.SortType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortFilter {

    private SortType sortType;

    private EntrySortField sortField;

}
