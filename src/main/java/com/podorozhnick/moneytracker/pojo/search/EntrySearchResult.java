package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.db.model.Entry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EntrySearchResult {

    private List<Entry> entries;

    private int pages;

    private int currentPage;

}
