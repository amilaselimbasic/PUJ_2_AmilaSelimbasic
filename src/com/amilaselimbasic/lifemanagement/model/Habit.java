package com.amilaselimbasic.lifemanagement.model;

public class Habit {

    private String name;
    private boolean done;

    public Habit(String name) {
        this.name=name;
        this.done=false;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done=done;
    }
}
