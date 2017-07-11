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
public class Category {

    @Id
    @GeneratedValue
    @JsonProperty
    private long id;

    @Column(name = "name")
    @JsonProperty
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @JsonProperty
    private CategoryType type;

}
