package com.podorozhnick.moneytracker.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="category")
@Getter
@Setter
@Accessors(chain = true)
@ToString(exclude = {Category.USER_FIELD, Category.ENTRIES_FIELD})
public class Category extends DbEntity {

    public static final String NAME_FIELD = "name";
    private static final String TYPE_FIELD = "type";
    static final String USER_FIELD = "user";
    static final String ENTRIES_FIELD = "entries";

    @Column(name = NAME_FIELD)
    @JsonProperty
    private String name;

    @Column(name = TYPE_FIELD)
    @Enumerated(EnumType.STRING)
    @JsonProperty
    private CategoryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_FIELD)
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = Entry.CATEGORY_FIELD, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Entry> entries;

}
