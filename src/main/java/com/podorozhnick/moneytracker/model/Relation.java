package com.podorozhnick.moneytracker.model;

import com.podorozhnick.moneytracker.model.enums.RelationType;

import javax.persistence.*;

@Entity
@Table(name = "relation")
public class Relation {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RelationType type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public long getId() {
        return id;
    }

    public Relation setId(long id) {
        this.id = id;
        return this;
    }

    public RelationType getType() {
        return type;
    }

    public Relation setType(RelationType type) {
        this.type = type;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Relation setCategory(Category category) {
        this.category = category;
        return this;
    }
}
