package com.podorozhnick.moneytracker.db.factory;

import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import com.podorozhnick.moneytracker.db.model.enums.RelationType;
import com.podorozhnick.moneytracker.pojo.search.CategorySearchParams;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchParamsFactory {

    public static CategorySearchParams createCategorySearchParams(Long userId, CategoryType type, RelationType relation) {
        CategorySearchParams searchParams = new CategorySearchParams();
        searchParams.setUserId(userId);
        searchParams.setType(type);
        searchParams.setRelation(relation);
        return searchParams;
    }

}
