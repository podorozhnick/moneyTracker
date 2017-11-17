package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.pojo.search.enums.CategorySortField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySortFilter extends SortFilter {

    private CategorySortField sortField;

}
