package com.podorozhnick.moneytracker.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name="category")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Category extends DbEntity {

    private static final String TYPE_FIELD = "type";
    private static final String NAME_FIELD = "name";

    @Column(name = NAME_FIELD)
    @JsonProperty
    private String name;

    @Column(name = TYPE_FIELD)
    @Enumerated(EnumType.STRING)
    @JsonProperty
    private CategoryType type;

}
