package com.amilaselimbasic.lifemanagement.model;

public class SleepRecord {

    private String date;
    private int hours;

    public SleepRecord (String date, int hours) {
        this.date=date;
        this.hours=hours;
    }

    public String getDate () {
        return date;
    }

    public int getHours () {
        return hours;
    }
}
