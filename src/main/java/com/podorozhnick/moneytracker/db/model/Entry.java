package com.podorozhnick.moneytracker.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    private long id;

    @Column(name = "date")
    @JsonProperty
    private Date date;

    @Column(name = "sum")
    @JsonProperty
    private Double sum;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @JsonProperty
    private Category category;

    @Column(name = "description")
    @JsonProperty
    private String description;

}
