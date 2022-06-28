package com.example.demo;

import com.example.demo.helpers.UserHelper;
import com.example.demo.models.TelegramUserModel;
import com.example.demo.workerCommand.BookCommand;
import com.example.demo.workerCommand.ChooseTime;
import com.example.demo.workerCommand.CommandWorker;
import com.example.demo.workerCommand.LoginCommand;
import com.example.demo.workerCommand.doctors.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    boolean ifNameRequared = false;

    @Override
    public String getBotUsername() {
        return "kentoa_bot";
    }

    @Override
    public String getBotToken() {
        return "5479372874:AAFTw7gQ62Ri69XoBl08haC2N46EEBStCWQ";
    }

    @Override
    public void onUpdateReceived(Update update) {

        KeyboardRow k1 = new KeyboardRow();

            k1.add(new KeyboardButton("LogIn"));
        k1.add(new KeyboardButton("Записаться"));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(k1));
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setText("Добро пожаловать");

        List<CommandWorker> list = new ArrayList<>();
        list.add(new LoginCommand());
        list.add(new BookCommand());
        list.add(new TerapevtBookCommand());
        list.add(new AllergologBookCommand());
        list.add(new OkulistBookCommand());
        list.add(new LorBookCommand());
        list.add(new HirurgBookCommand());
        list.add(new GinekologBookCommand());
        list.add(new ChooseTime());


        SendMessage s;
        for (CommandWorker c : list){
            if ((s = c.start(update))!=null) {
                sendMessage = s;
                break;
            }
        }
        if (ifNameRequared){
            TelegramUserModel telegramUserModel = new TelegramUserModel();
            telegramUserModel.setName(update.getMessage().getText());

            User user;
            user = update.getMessage().getFrom();
            telegramUserModel.setTelegramId(user.getId().toString());
        }
        if (update.getMessage().getText().equals("Введите свое имя")){
            ifNameRequared = true;
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
