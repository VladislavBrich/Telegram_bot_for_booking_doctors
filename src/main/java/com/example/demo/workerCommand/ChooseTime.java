package com.example.demo.workerCommand;

import com.example.demo.helpers.DoctorHelper;
import com.example.demo.helpers.TimeControll;
import com.example.demo.helpers.UserHelper;
import com.example.demo.models.BookModel;
import com.example.demo.models.TelegramUserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class ChooseTime implements CommandWorker {
    @Override
    public SendMessage start(Update update) {
        TimeControll timeControll = new TimeControll();
        List<String> list = timeControll.getTimes();
        boolean ifThisCommand =false;
        for (String str : list){
            if (update.getMessage().getText().equals(str)){
                ifThisCommand = true;
            }
        }
        if (!ifThisCommand){
            return null;
        }
        BookModel bookModel = new BookModel();
        bookModel.setTime(update.getMessage().getText());

        TelegramUserModel telegramUserModel =
                UserHelper.getUser(update.getMessage().getFrom().getId().toString());

        bookModel.setDoctorEnum(telegramUserModel.getDoctorEnum());
        bookModel.setId(update.getMessage().getFrom().getId());
        DoctorHelper.save(bookModel);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Вы успешно записались к врачу");
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
