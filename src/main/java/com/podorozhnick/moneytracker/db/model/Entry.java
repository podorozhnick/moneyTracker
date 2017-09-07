package com.podorozhnick.moneytracker.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "entry")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Entry extends DbEntity {

    public static final String DATE_FIELD = "date";
    private static final String SUM_FIELD = "sum";
    private static final String DESCRIPTION_FIELD = "description";
    public static final String CATEGORY_FIELD = "category";

    @Column(name = DATE_FIELD)
    @JsonProperty
    private Date date;

    @Column(name = SUM_FIELD)
    @JsonProperty
    private Double sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CATEGORY_FIELD)
    @JsonProperty
    private Category category;

    @Column(name = DESCRIPTION_FIELD)
    @JsonProperty
    private String description;

}
