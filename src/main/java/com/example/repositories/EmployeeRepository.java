package com.example.repositories;

import com.example.entities.Customer;
import com.example.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query(value="delete from employee where user_id = ?1", nativeQuery = true)
    public Employee deleteEmployee(int user_id);

    @Query(value = "select * from employee where user_id=?1", nativeQuery = true)
    public Employee findEmployee(int user_id);
}
