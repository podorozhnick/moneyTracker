package com.podorozhnick.moneytracker.pojo.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntrySearchFilter extends SearchFilter {

    private EntrySearchParams searchParams;

    private EntrySortFilter sortFilter;

}
