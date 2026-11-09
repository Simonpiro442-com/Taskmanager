package com.example.taskmanager;

import org.springframework.stereotype.Indexed;

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; 

    private String description; 

    private boolean completed; 

    // Required by JPA
    public Task() {
    }

    //Constructor used when creating a new task
    public Task(String title, String description, boolean completed) {
        this.title = title; 
        this.description = description; 
        this.completed = completed; 
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed; 
    }

    public void setTitle(String title) {
        this.title = title; 
    }

    public void setDescription(String description) {
        this.description = description; 
    }

    public void setCompleted(boolean completed) {
        this.completed = completed; 
    }
    
}
