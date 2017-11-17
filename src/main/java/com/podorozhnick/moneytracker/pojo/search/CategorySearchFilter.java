package com.podorozhnick.moneytracker.pojo.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySearchFilter extends SearchFilter {

    private CategorySearchParams searchParams;

    private CategorySortFilter sortFilter;

}
