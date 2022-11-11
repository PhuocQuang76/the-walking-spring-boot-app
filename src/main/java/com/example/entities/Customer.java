package com.example.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name="customer")
public class Customer {
    @Id
    @Column(name="customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
//    @JsonIgnore
//    private Set<Purchase> purchases;
}
