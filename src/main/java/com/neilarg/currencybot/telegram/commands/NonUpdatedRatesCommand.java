package com.neilarg.currencybot.telegram.commands;

import com.neilarg.currencybot.model.ExchangeRate;
import com.neilarg.currencybot.model.Role;
import com.neilarg.currencybot.repository.ExchangeRateRepository;
import com.neilarg.currencybot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.neilarg.currencybot.util.Constants.DATE_FORMAT;
import static com.neilarg.currencybot.util.Keyboards.ADMIN_KEYBOARD;
import static com.neilarg.currencybot.util.Keyboards.USER_KEYBOARD;

@Slf4j
public class NonUpdatedRatesCommand extends BotCommand {

    private final ExchangeRateRepository exchangeRateRepository;
    private final UserService userService;

    public NonUpdatedRatesCommand(String command,
                                  ExchangeRateRepository exchangeRateRepository,
                                  UserService userService) {
        super(command, command);
        this.exchangeRateRepository = exchangeRateRepository;
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());
        sendMessage.setReplyMarkup(USER_KEYBOARD);
        if (userService.getUser(chat.getId().toString()).getRole().equals(Role.ADMIN)) {
            List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll(Sort.by(Sort.Direction.DESC, "updateDate"));
            Date nowDate = new Date();
            sendMessage.setText("Список курсов валют, которые давно не обновлялись:\n" +
                    exchangeRates.stream().filter(item -> nowDate.getTime() - item.getUpdateDate().getTime() >= 30 * 60 * 1000)
                            .map(item -> item.getBank().getName() +
                                    " (" +
                                    item.getCurrency().getCode() +
                                    ")\n" +
                                    DATE_FORMAT.format(item.getUpdateDate()))
                            .collect(Collectors.joining("\n")));
            sendMessage.setReplyMarkup(ADMIN_KEYBOARD);
        }
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("[SEND_MESSAGE_ERROR] TO [%s]".formatted(chat.getId().toString()), e);
        }
    }
}
