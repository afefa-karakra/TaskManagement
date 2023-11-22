package com.example.taskmanagement;


public class Task {
    public String title;

    public Task(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title ;
    }
}

