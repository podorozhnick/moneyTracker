package com.podorozhnick.moneytracker.pojo.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SearchFilter {

    public abstract SearchParams getSearchParams();
    public abstract SortFilter getSortFilter();

    private PageFilter pageFilter;

}
