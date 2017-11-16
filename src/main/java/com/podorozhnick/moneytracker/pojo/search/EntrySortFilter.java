package com.podorozhnick.moneytracker.pojo.search;

import com.podorozhnick.moneytracker.pojo.search.enums.EntrySortField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EntrySortFilter extends SortFilter {

    @NotNull
    private EntrySortField sortField;

}
