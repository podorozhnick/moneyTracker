package com.podorozhnick.moneytracker.pojo.search;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EntrySearchParams extends SearchParams {

    private Date from;

    private Date to;

    private Long userId;

}
