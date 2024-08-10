package com.techlabs.app.repository;

import com.techlabs.app.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findByFullName(String fullName);
    Bank findByAbbreviation(String abbreviation);
}
