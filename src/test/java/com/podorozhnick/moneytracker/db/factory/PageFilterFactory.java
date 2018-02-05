package com.podorozhnick.moneytracker.db.factory;

import com.podorozhnick.moneytracker.pojo.search.PageFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageFilterFactory {

    public static PageFilter createPageFilter(Integer count, Integer page) {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setCount(count);
        pageFilter.setPage(page);
        return pageFilter;
    }

}
