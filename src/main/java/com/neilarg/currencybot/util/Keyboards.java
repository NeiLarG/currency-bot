package com.neilarg.currencybot.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public final class Keyboards {

    // USERS KEYBOARD ROWS
    private static final KeyboardRow USER_KEYBOARD_ROW_1 = new KeyboardRow(List.of(new KeyboardButton("/usd"),
            new KeyboardButton("/eur"),
            new KeyboardButton("/rub")));
    // KEYBOARD MARKUPS
    public static final ReplyKeyboard USER_KEYBOARD = new ReplyKeyboardMarkup(
            List.of(USER_KEYBOARD_ROW_1),
            true,
            null,
            null,
            null);
    // ADMIN KEYBOARD ROWS
    private static final KeyboardRow ADMIN_KEYBOARD_ROW_1 = new KeyboardRow(List.of(new KeyboardButton("/users"),
            new KeyboardButton("/non_updated_rates")));
    public static final ReplyKeyboard ADMIN_KEYBOARD = new ReplyKeyboardMarkup(
            List.of(USER_KEYBOARD_ROW_1, ADMIN_KEYBOARD_ROW_1),
            true,
            null,
            null,
            null);

}
