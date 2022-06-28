package com.example.demo.helpers;

import com.example.demo.models.BookModel;
import com.example.demo.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorHelper {
    private static DoctorHelper doctorHelper = null;

    public DoctorHelper(BookRepo bookRepo) {
        doctorHelper = this;
    }

    @Autowired
    BookRepo bookRepo;

    public static void save(BookModel b){
        doctorHelper.bookRepo.save(b);
    }
    public static List<String> getFreeTime(DoctorEnum d){
        TimeControll timeControll = new TimeControll();
        List<BookModel> bookModelList = doctorHelper.bookRepo.
                findBookModelsByDoctorEnum(d);
        List<String> freeTimes = new ArrayList<>();
        freeTimes  = timeControll.getTimes();
        List<String> list = new ArrayList<>();
        for (BookModel b: bookModelList){
            for (String str : freeTimes){
                if (b.getTime().equals(str)){
                    list.add(b.getTime());
                }
            }
        }
        freeTimes.removeAll(list);
        return freeTimes;
    }

}
