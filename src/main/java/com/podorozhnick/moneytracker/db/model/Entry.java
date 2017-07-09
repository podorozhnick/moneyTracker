package com.podorozhnick.moneytracker.db.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "entry")
public class Entry {

    @Id
    @GeneratedValue
    @Expose
    private long id;

    @Column(name = "date")
    @Expose
    private Date date;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @Expose
    private Category category;

    @Column(name = "description")
    @Expose
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
