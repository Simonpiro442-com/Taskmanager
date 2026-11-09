package com.example.taskmanager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid; 

import java.util.List;

@RestController 
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService; 

    public TaskController(TaskService taskService) {
        this.taskService = taskService; 
    }

    @GetMapping 
    public List<Task> getTasks(
           @RequestParam (required = false) Boolean completed) {

        return taskService.getAllTasks(completed); 
        }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task); 
    }

    @PostMapping 
    public ResponseEntity<Task> createTask(
           @Valid @RequestBody TaskRequest request) {

      Task newTask = taskService.createTask(request); 

      return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(newTask); 
          }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id, 
            @Valid @RequestBody TaskRequest request) {
        
        Task updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updatedTask); 

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteTask(
           @PathVariable Long id){
        
        taskService.deleteTask(id); 
        return ResponseEntity.noContent().build(); 
        }
    
}