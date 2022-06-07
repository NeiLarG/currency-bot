package com.neilarg.currencybot.repository;

import com.neilarg.currencybot.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByName(String name);

}
