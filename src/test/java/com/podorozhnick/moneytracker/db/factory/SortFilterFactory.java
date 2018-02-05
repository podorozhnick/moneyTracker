package com.podorozhnick.moneytracker.db.factory;

import com.podorozhnick.moneytracker.pojo.search.CategorySortFilter;
import com.podorozhnick.moneytracker.pojo.search.enums.CategorySortField;
import com.podorozhnick.moneytracker.pojo.search.enums.SortType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SortFilterFactory {

    public static CategorySortFilter createCategorySortFilter(SortType sortType, CategorySortField sortField) {
        CategorySortFilter sortFilter = new CategorySortFilter();
        sortFilter.setSortType(sortType);
        sortFilter.setSortField(sortField);
        return sortFilter;
    }

}
