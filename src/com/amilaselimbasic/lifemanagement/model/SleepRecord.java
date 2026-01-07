package com.amilaselimbasic.lifemanagement.model;

public class SleepRecord {

    private String id;
    private String date;
    private int hours;

    public SleepRecord (String id, String date, int hours) {
        this.id=id;
        this.date=date;
        this.hours=hours;
    }

    public String getId () {
        return id;
    }

    public String getDate () {
        return date;
    }

    public int getHours () {
        return hours;
    }
}
