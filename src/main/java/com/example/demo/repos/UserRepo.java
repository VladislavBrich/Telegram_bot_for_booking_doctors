package com.example.demo.repos;

import com.example.demo.models.TelegramUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface UserRepo extends JpaRepository<TelegramUserModel, Long> {
    TelegramUserModel findTelegramUserModelByTelegramId(String TelegramId);
}
