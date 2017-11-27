package com.podorozhnick.moneytracker.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class User extends DbEntity {

    public static final String LOGIN_FIELD = "login";
    public static final String EMAIL_FIELD = "email";
    private static final String NAME_FIELD = "name";
    private static final String SURNAME_FIELD = "surname";
    private static final String PASSWORD_FIELD = "password";

    @Column(name = LOGIN_FIELD, unique = true)
    @NotEmpty
    @Size(min = 2, max = 25)
    private String login;

    @Column(name = EMAIL_FIELD, unique = true)
    @NotEmpty
    @Email
    private String email;

    @Column(name = PASSWORD_FIELD)
    @NotEmpty
    @Size(min = 5, max = 25)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = NAME_FIELD)
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = SURNAME_FIELD)
    @Size(min = 2, max = 50)
    private String surname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = Category.USER_FIELD, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Category> categories;

}
