package com.podorozhnick.moneytracker.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class User extends DbEntity {

    public static final String LOGIN_FIELD = "login";
    private static final String NAME_FIELD = "name";
    private static final String SURNAME_FIELD = "surname";
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";

    @Column(name = NAME_FIELD)
    private String name;

    @Column(name = SURNAME_FIELD)
    private String surname;

    @Column(name = EMAIL_FIELD)
    private String email;

    @Column(name = LOGIN_FIELD)
    private String login;

    @Column(name = PASSWORD_FIELD)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = Category.USER_FIELD, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Category> categories;

}
