package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySearchParams extends SearchParams {

    private CategoryType type;

    private Long userId;

}
