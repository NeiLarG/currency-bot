package com.neilarg.currencybot.service;

import com.neilarg.currencybot.model.Bank;
import com.neilarg.currencybot.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank getBank(String bankName) {
        Bank bank = bankRepository.findByName(bankName);
        if (bank == null) {
            log.info("[NEW_BANK_FOUNDED] [A new bank - %s]".formatted(bankName));
        }
        return bank;
    }

}
