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
@NamedEntityGraph(name = Entry.ENTRY_CATEGORY_GRAPH, attributeNodes = @NamedAttributeNode(Entry.CATEGORY_FIELD))
@Getter
@Setter
@Accessors(chain = true)
@ToString(exclude = {Entry.CATEGORY_FIELD})
public class Entry extends DbEntity {

    public static final String ENTRY_CATEGORY_GRAPH = "Entry[Category]";

    public static final String DATE_FIELD = "date";
    private static final String SUM_FIELD = "amount";
    private static final String DESCRIPTION_FIELD = "description";
    public static final String CATEGORY_FIELD = "category";

    @Column(name = DATE_FIELD)
    @JsonProperty
    private Date date;

    @Column(name = SUM_FIELD)
    @JsonProperty
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CATEGORY_FIELD)
    @JsonProperty
    private Category category;

    @Column(name = DESCRIPTION_FIELD)
    @JsonProperty
    private String description;

}
