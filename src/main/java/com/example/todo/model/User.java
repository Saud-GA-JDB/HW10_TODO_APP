package com.example.todo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Column(unique = true)
    private String emailAddress;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // one user can have only one profile
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile userProfile;

    // user can have more than one recipe
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Item> itemList;

    // user can have more than one category
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Category> categoryList;

}
