package com.podorozhnick.moneytracker.pojo.search;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PageFilter {

    @Positive
    private Integer page;

    @NotNull
    @Range(min = -1)
    private Integer count;

}
