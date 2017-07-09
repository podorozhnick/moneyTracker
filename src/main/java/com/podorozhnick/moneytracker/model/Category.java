package com.podorozhnick.moneytracker.model;

import com.google.gson.annotations.Expose;
import com.podorozhnick.moneytracker.model.enums.CategoryType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Expose
    private Category parentCategory;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Relation> relations;

    @Transient
    @Expose
    private Long parentId;

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

    public Category getParentCategory() {
        return parentCategory;
    }

    public Category setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
        return this;
    }

    public List<Relation> getRelations() {
        if (this.relations == null) {
            this.relations = new ArrayList<>();
        }
        return relations;
    }

    public Category setRelations(List<Relation> relations) {
        this.relations = relations;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public Category setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }
}
