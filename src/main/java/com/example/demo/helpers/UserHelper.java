package com.example.demo.helpers;

import com.example.demo.models.TelegramUserModel;
import com.example.demo.repos.UserRepo;
import org.checkerframework.checker.units.qual.A;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserHelper {
    private static UserHelper helper = null;

    public UserHelper() {
        helper = this;
    }

    @Autowired
    public UserRepo userRepo;

    public static void saveUserToDb(TelegramUserModel t){
        helper.userRepo.save(t);
    }
    public static boolean findUserByTgId(String tgId){
        if (helper.userRepo.findTelegramUserModelByTelegramId(tgId)!=null){
            return false;
        }
        return true;
    }
    public static TelegramUserModel getUser(String tgId){
        TelegramUserModel telegramUserModel =
                helper.userRepo.findTelegramUserModelByTelegramId(tgId);
        return telegramUserModel;
    }
}
