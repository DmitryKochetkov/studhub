package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {
    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    private List<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="followers",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="follower_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "follower_id"})
    )
    private List<User> followers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="followers",
            joinColumns=@JoinColumn(name="follower_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "follower_id"})
    )
    private List<User> following;
}