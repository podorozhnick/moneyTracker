package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.db.model.Entry;
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
public class EntrySearchResult {

    List<Entry> entries;
    int pages;
    int currentPage;

}
