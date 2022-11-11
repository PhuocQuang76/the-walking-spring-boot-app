package com.example.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="purchase")
public class Purchase {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "purchase_id")
        private int id;

        @ManyToOne
        @JoinColumn(name="user_id", nullable = false)
        private User user;

//        @Column(name = "order_status_id", nullable = false)
//        private PurchaseStatus purchaseStatusId;

        @Enumerated(EnumType.STRING)
        private EPurchaseStatus purchaseStatus;

//        @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchase")
//        @JsonIgnore
//        private Set<Product> products;

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        private int quantity;

        private double price;

        private double total;

        @Column(name = "date_created")
        @CreationTimestamp
        private Date dateCreated;

        @Column(name = "last_updated")
        @UpdateTimestamp
        private Date lastUpdated;

//        @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchase")
//        @JsonIgnore
//        private Set<PurchaseDetail> purchaseDetails;



}
