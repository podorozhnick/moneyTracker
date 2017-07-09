package com.podorozhnick.moneytracker.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category_relations")
public class CategoryRelations {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "category_id")
    private Category category;

    @Expose
    @OneToMany
    @JoinTable(
            name = "category_parents",
            joinColumns = @JoinColumn(name = "relation_category_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_category_id")
    )
    private List<Category> parents;

    @Expose
    @OneToMany
    @JoinTable(
            name = "category_children",
            joinColumns = @JoinColumn(name = "relation_category_id"),
            inverseJoinColumns = @JoinColumn(name = "children_category_id")
    )
    private Set<Category> children;

    public List<Category> getParents() {
        if (this.parents == null) {
            this.parents = new ArrayList<>();
        }
        return this.parents;
    }

    public CategoryRelations setParents(List<Category> parents) {
        this.parents = parents;
        return this;
    }

    public Set<Category> getChildren() {
        if (this.children == null) {
            this.children = new HashSet<>();
        }
        return this.children;
    }

    public CategoryRelations setChildren(Set<Category> children) {
        this.children = children;
        return this;
    }

    public long getId() {
        return id;
    }

    public CategoryRelations setId(long id) {
        this.id = id;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public CategoryRelations setCategory(Category category) {
        this.category = category;
        return this;
    }
}
