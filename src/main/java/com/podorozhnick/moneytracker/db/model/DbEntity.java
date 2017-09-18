package com.podorozhnick.moneytracker.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class DbEntity {

    public static final String ID_FIELD = "id";

    @Id
    @GeneratedValue
    @Column(name = ID_FIELD)
    @JsonProperty
    private long id;

}
