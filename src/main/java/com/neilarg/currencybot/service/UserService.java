package com.neilarg.currencybot.service;

import com.neilarg.currencybot.model.Role;
import com.neilarg.currencybot.model.User;
import com.neilarg.currencybot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String chatId) {
        User user = userRepository.findByChatId(chatId);
        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            user.setRole(Role.USER);
            log.info("[CREATE_USER] [%s]".formatted(chatId));
        }
        user.setUpdateDate(new Date());
        user = userRepository.save(user);
        return user;
    }


}
