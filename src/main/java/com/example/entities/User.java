package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter

@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @Column(name="user_name")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="address")
    private String address;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateOrdered;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnore
    private Set<Purchase> purchases;

    public User() {
    }

    public User(String username, String email, String password) {
    }

    public User(int userId, String username, String email, Set<Role> roles, String password, String address) {
        this.userId = userId;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public User(String username, String email, String password, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public User(String username, String email, Set<Role> roles, String password, String address) {
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public User(int userId, Set<Role> roles, String username, String password, String email, String address, Date dateOrdered, Date lastUpdated, Set<Purchase> purchases) {
        this.userId = userId;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.dateOrdered = dateOrdered;
        this.lastUpdated = lastUpdated;
        this.purchases = purchases;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
