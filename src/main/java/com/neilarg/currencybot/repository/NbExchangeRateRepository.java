package com.neilarg.currencybot.repository;

import com.neilarg.currencybot.model.NbExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NbExchangeRateRepository extends JpaRepository<NbExchangeRate, Long> {
}
