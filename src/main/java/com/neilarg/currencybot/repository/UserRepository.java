package com.neilarg.currencybot.repository;

import com.neilarg.currencybot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByChatId(String chatId);
}
