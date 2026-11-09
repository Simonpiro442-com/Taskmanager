package com.example.taskmanager;

import jakarta.validation.constraints.NotBlank; 

public class TaskRequest {

    @NotBlank(message = "Title is required and cannot be blank")
    private  String title; 

    @NotBlank(message = "Description is required and cannot be blank")
    private String description; 
    
    private boolean completed; 


    public TaskRequest() {

    }

    public String getTitle() {
        return title; 
    }

    public void setTitle(String title) {
        this.title = title; 
    }

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        this.description = description; 
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed; 
    }
    
}
