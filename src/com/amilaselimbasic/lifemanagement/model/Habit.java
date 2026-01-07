package com.amilaselimbasic.lifemanagement.model;

public class Habit {

    private String id;
    private String name;
    private boolean done;

    public Habit(String id, String name, boolean done) {
        this.id=id;
        this.name=name;
        this.done=false;
    }

    public String getId() {
        return id;
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
