package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.db.model.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategorySearchResult {

    List<Category> categories;
    int page;
    int currentPage;

}
