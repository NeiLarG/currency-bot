package com.neilarg.currencybot.telegram.commands;

import com.neilarg.currencybot.model.Role;
import com.neilarg.currencybot.repository.CurrencyRepository;
import com.neilarg.currencybot.repository.ExchangeRateRepository;
import com.neilarg.currencybot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;

import static com.neilarg.currencybot.util.Keyboards.ADMIN_KEYBOARD;
import static com.neilarg.currencybot.util.Keyboards.USER_KEYBOARD;
import static com.neilarg.currencybot.util.Messages.CURRENCY_MESSAGE;

@Slf4j
public class CurrencyCommand extends BotCommand {

    private final UserService userService;

    private final ExchangeRateRepository exchangeRateRepository;

    private final CurrencyRepository currencyRepository;

    private final String currencyCode;

    public CurrencyCommand(String currencyCode,
                           UserService userService,
                           CurrencyRepository currencyRepository,
                           ExchangeRateRepository exchangeRateRepository) {
        super(currencyCode, currencyCode);
        this.currencyCode = currencyCode;
        this.userService = userService;
        this.currencyRepository = currencyRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder sb = new StringBuilder();
        exchangeRateRepository.findByCurrency(currencyRepository.findByCode(currencyCode))
                .stream()
                .sorted(Comparator.comparing(o -> o.getBank().getName()))
                .forEach(rate -> {
                    String bankName = rate.getBank().getName();
                    String purchase = rate.getPurchase().toString();
                    String sale = rate.getSale().toString();
                    String units = rate.getCurrency().getUnits().toString();
                    String code = rate.getCurrency().getCode();
                    String type = rate.getBank().getType().name();
                    if (type.equals("DEPARTMENT")) {
                        type = "\uD83C\uDFE6";
                    } else {
                        type = "\uD83D\uDCF1";
                    }
                    sb.append(CURRENCY_MESSAGE.formatted(
                            type,
                            bankName,
                            purchase,
                            units,
                            code,
                            sale,
                            units,
                            code));
                });
        SendMessage message = new SendMessage();
        message.setParseMode("HTML");
        message.setChatId(chat.getId().toString());
        message.setText(sb.toString());
        message.setReplyMarkup(userService.getUser(chat.getId().toString()).getRole().equals(Role.ADMIN) ? ADMIN_KEYBOARD : USER_KEYBOARD);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("[SEND_MESSAGE_ERROR] TO [%s]".formatted(chat.getId().toString()), e);
        }

    }

}
