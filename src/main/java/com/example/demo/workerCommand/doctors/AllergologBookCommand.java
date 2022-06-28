package com.example.demo.workerCommand.doctors;

import com.example.demo.helpers.DoctorEnum;
import com.example.demo.helpers.DoctorHelper;
import com.example.demo.helpers.UserHelper;
import com.example.demo.models.TelegramUserModel;
import com.example.demo.workerCommand.CommandWorker;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class AllergologBookCommand implements CommandWorker {
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Аллерголог")){
            return null;
        }
        TelegramUserModel telegramUserModel = UserHelper.getUser(update.getMessage().getFrom().getId().toString());
        telegramUserModel.setDoctorEnum(DoctorEnum.ALLERGOLOG);
        UserHelper.saveUserToDb(telegramUserModel);
        return sendDefaultMessage(update);
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage()
                .getChatId().toString());
        sendMessage.setText("Выберите подходящее время");

        List<String> list = DoctorHelper.getFreeTime(
                DoctorEnum.ALLERGOLOG);

        KeyboardRow k1 = new KeyboardRow();
        k1.add(new KeyboardButton(list.get(0)));
        k1.add(new KeyboardButton(list.get(1)));

        KeyboardRow k2 = new KeyboardRow();
        if (list.size()>2){
            for (int i = 2; i < list.size(); i++) {
                k2.add(new KeyboardButton(list.get(i)));
            }
        }
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> list1 = new ArrayList<>();
        list1.add(k1);
        list1.add(k2);
        replyKeyboardMarkup.setKeyboard(list1);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }
}
