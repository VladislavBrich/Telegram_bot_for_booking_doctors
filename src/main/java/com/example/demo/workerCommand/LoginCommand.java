package com.example.demo.workerCommand;

import com.example.demo.helpers.UserHelper;
import com.example.demo.models.TelegramUserModel;
import com.example.demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

@Component
public class LoginCommand implements CommandWorker{

    @Autowired
    UserRepo userRepo;

    @Override
    public SendMessage start(Update update) {
        TelegramUserModel telegramUserModel = new TelegramUserModel();
        User user;
        user = update.getMessage().getFrom();
        if (update.hasMessage()&&((!update.getMessage().getText().equals("LogIn")
        &&!update.getMessage().getText().equals("Хочу остаться анонимом")))
        &&!update.getMessage().getText().equals("Ввести свое имя")) {
            return null;
        }
        else if (update.getMessage().getText().equals("LogIn")){
            SendMessage sendMessage = new SendMessage();
            return sendMessage = sendDefaultMessage(update);
        }
        else if (update.getMessage().getText().equals("Хочу остаться анонимом")){
            telegramUserModel.setUsername(user.getUserName());
            telegramUserModel.setTelegramId(String.valueOf(user.getId()));
            UserHelper.saveUserToDb(telegramUserModel);
        }else if (update.getMessage().getText().equals("Ввести свое имя")){
            telegramUserModel.setName(user.getUserName());
            telegramUserModel.setUsername(user.getUserName());
            telegramUserModel.setName(user.getFirstName());
        }
        return null;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Ведите свое имя");

        KeyboardRow keyboard = new KeyboardRow();
        keyboard.add(new KeyboardButton("Хочу остаться анонимом"));
        keyboard.add(new KeyboardButton("Ввести свое имя"));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboard));

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }
}
