package com.techlabs.app.repository;

import com.techlabs.app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByFirstNameAndLastName(String firstName, String lastName);
}
