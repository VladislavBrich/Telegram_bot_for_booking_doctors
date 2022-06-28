package com.example.demo.repos;

import com.example.demo.helpers.DoctorEnum;
import com.example.demo.models.BookModel;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<BookModel, Long> {
    List<BookModel> findBookModelsByDoctorEnum(DoctorEnum d);
}
