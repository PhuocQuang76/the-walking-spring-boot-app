package com.example.repositories;

import com.example.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    @Query(value="delete from customer where user_id = ?1", nativeQuery = true)
    public Customer deleteCustomer(int user_id);

    @Query(value = "select * from customer where user_id=?1", nativeQuery = true)
    public Customer findCustomer(int user_id);
}
