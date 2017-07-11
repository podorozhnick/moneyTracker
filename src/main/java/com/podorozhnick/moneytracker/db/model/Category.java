package com.podorozhnick.moneytracker.db.model;

import com.google.gson.annotations.Expose;
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
    @Expose
    private long id;

    @Column(name = "name")
    @Expose
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @Expose
    private CategoryType type;

}
