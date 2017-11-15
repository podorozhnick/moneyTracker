package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.pojo.search.enums.EntrySortField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntrySortFilter extends SortFilter {

    private EntrySortField sortField;

}
