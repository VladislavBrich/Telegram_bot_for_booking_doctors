package com.example.demo.helpers;

import java.util.ArrayList;
import java.util.List;

public class TimeControll {
    private List<String> times = new ArrayList<>();

    public TimeControll() {
        this.times.add("10.00");
        this.times.add("11.00");
        this.times.add("12.00");

        this.times.add("13.00");
        this.times.add("14.00");
        this.times.add("15.00");

        this.times.add("16.00");
        this.times.add("17.00");
        this.times.add("18.00");
    }

    public List<String> getTimes() {
        return times;
    }
}
