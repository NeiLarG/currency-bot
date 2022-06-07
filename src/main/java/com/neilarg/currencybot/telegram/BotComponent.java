package com.neilarg.currencybot.telegram;

import com.neilarg.currencybot.model.Role;
import com.neilarg.currencybot.repository.CurrencyRepository;
import com.neilarg.currencybot.repository.ExchangeRateRepository;
import com.neilarg.currencybot.repository.UserRepository;
import com.neilarg.currencybot.service.UserService;
import com.neilarg.currencybot.telegram.commands.CurrencyCommand;
import com.neilarg.currencybot.telegram.commands.NonUpdatedRatesCommand;
import com.neilarg.currencybot.telegram.commands.UsersCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.neilarg.currencybot.util.Keyboards.ADMIN_KEYBOARD;
import static com.neilarg.currencybot.util.Keyboards.USER_KEYBOARD;
import static com.neilarg.currencybot.util.Messages.WELCOM_MESSAGE;

@Slf4j
@Component
public class BotComponent extends TelegramLongPollingCommandBot {

    @Value("${bot.nickname}")
    private String nickname;

    @Value("${bot.token}")
    private String token;

    private final UserService userService;

    @Autowired
    public BotComponent(UserService userService,
                        CurrencyRepository currencyRepository,
                        ExchangeRateRepository exchangeRateRepository,
                        UserRepository userRepository) {
        this.userService = userService;

        register(new CurrencyCommand("USD", userService, currencyRepository, exchangeRateRepository));
        register(new CurrencyCommand("EUR", userService, currencyRepository, exchangeRateRepository));
        register(new CurrencyCommand("RUB", userService, currencyRepository, exchangeRateRepository));
        register(new UsersCommand("/users", userService, userRepository));
        register(new NonUpdatedRatesCommand("non_updated_rates", exchangeRateRepository, userService));
    }

    @Override
    public String getBotUsername() {
        return nickname;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String firstName = update.getMessage().getChat().getFirstName();
        Role userRole = userService.getUser(chatId).getRole();
        SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(WELCOM_MESSAGE.formatted(firstName));
            sendMessage.setReplyMarkup(userRole.equals(Role.ADMIN) ? ADMIN_KEYBOARD : USER_KEYBOARD);
                    try {
            execute(sendMessage);
            log.info("[SEND_MESSAGE] [%s]".formatted(chatId));
        } catch (TelegramApiException e) {
            log.error("[SEND_MESSAGE_ERROR] TO [%s]".formatted(chatId), e);
        }
    }

}
