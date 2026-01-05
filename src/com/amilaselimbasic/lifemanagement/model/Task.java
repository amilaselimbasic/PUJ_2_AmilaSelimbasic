package com.amilaselimbasic.lifemanagement.model;

public class Task {
    private String name;
    private boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    public String getName(){
        return name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done){
        this.done=done;
    }
}
