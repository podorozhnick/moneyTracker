package com.podorozhnick.moneytracker.pojo.search;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EntrySearchFilter extends SearchFilter {

    private EntrySearchParams searchParams;

    @NotNull
    private EntrySortFilter sortFilter;

}
