package com.podorozhnick.moneytracker.db.model;

import com.google.gson.annotations.Expose;
import com.podorozhnick.moneytracker.db.model.enums.CategoryType;

import javax.persistence.*;

@Entity
@Table(name="category")
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

    public long getId() {
        return id;
    }

    public Category setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryType getType() {
        return type;
    }

    public Category setType(CategoryType type) {
        this.type = type;
        return this;
    }

}
