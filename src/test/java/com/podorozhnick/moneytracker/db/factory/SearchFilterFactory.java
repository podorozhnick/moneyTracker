package com.podorozhnick.moneytracker.db.factory;

import com.podorozhnick.moneytracker.pojo.search.CategorySearchFilter;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchParams;
import com.podorozhnick.moneytracker.pojo.search.CategorySortFilter;
import com.podorozhnick.moneytracker.pojo.search.PageFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchFilterFactory {

    public static CategorySearchFilter createCategorySearchFilter(PageFilter pageFilter, CategorySortFilter sortFilter, CategorySearchParams searchParams) {
        CategorySearchFilter categorySearchFilter = new CategorySearchFilter();
        categorySearchFilter.setPageFilter(pageFilter);
        categorySearchFilter.setSortFilter(sortFilter);
        categorySearchFilter.setSearchParams(searchParams);
        return categorySearchFilter;
    }

}
