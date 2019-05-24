package com.p.android;

import java.util.List;

public class User {
    private String name;
    private String id;
    private List<MyTask> tasks;

    public User(String id, String name, List<MyTask> tasks) {
        this.name = name;
        this.id = id;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MyTask> getTask() {
        return tasks;
    }

    public void setTask(List<MyTask> tasks) {
        this.tasks = tasks;
    }
}
