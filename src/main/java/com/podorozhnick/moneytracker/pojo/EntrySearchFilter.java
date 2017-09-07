package com.podorozhnick.moneytracker.pojo;

import com.podorozhnick.moneytracker.pojo.enums.SortType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EntrySearchFilter {

    private Date from;

    private Date to;

    private int page;

    private int count;

    private String sortField;

    private SortType sortType;

}
