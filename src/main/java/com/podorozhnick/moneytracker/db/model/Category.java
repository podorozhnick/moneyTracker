package com.podorozhnick.moneytracker.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import com.podorozhnick.moneytracker.db.model.enums.RelationType;
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
@ToString(exclude = {Category.USER_FIELD, Category.ENTRIES_FIELD, Category.CHILDREN_FIELD})
public class Category extends DbEntity {

    public static final String NAME_FIELD = "name";
    public static final String USER_FIELD = "owner";
    public static final String TYPE_FIELD = "type";
    public static final String RELATION_FIELD = "relation";
    static final String PARENT_FIELD = "parent";
    static final String ENTRIES_FIELD = "entries";
    static final String CHILDREN_FIELD = "children";

    @Column(name = NAME_FIELD)
    private String name;

    @Column(name = TYPE_FIELD)
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_FIELD)
    @JsonIgnore
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = Entry.CATEGORY_FIELD, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Entry> entries;

    @ManyToOne
    @JoinColumn(name = PARENT_FIELD)
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = PARENT_FIELD, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Category> children;

    @Column(name = RELATION_FIELD)
    @Enumerated(EnumType.STRING)
    private RelationType relation = RelationType.CHILD;

}
