package com.podorozhnick.moneytracker.db.model;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "entry")
@Getter
@Setter
@Accessors(chain = true)
@ToString
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

}
