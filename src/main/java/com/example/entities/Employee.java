package com.example.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="employee")
public class Employee {
    @Id
    @Column(name="employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

}
