package com.podorozhnick.moneytracker.pojo.search;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class SearchFilter {

    public abstract SearchParams getSearchParams();
    public abstract SortFilter getSortFilter();

    @NotNull
    private PageFilter pageFilter;

}
