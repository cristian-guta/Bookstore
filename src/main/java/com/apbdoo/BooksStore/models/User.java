package com.apbdoo.BooksStore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany
    private List<Book> favouriteBooks;

    @NotNull
    @Column(name = "status")
    private Boolean deactivated = false;

    public User() {
    }

    public User(String username, String passwordHash, String role, String address) {
        super();
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.address = address;
    }


}