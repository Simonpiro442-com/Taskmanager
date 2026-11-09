package com.example.taskmanager;

import org.springframework.stereotype.Service; 

import java.util.List;

@Service 
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository; 

    }

    public List<Task> getAllTasks(Boolean completed) {

        if (completed == null) {
            return taskRepository.findAll(); 
        }

        return taskRepository.findByCompleted(completed);
    }

    public Task getTaskById(Long id) {

        return taskRepository.findById(id)
                 .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(TaskRequest request) {
        Task task = new Task(
            request.getTitle(), 
            request.getDescription(),
            false
        );

        return taskRepository.save(task); 
    }

    public Task updateTask(Long id, TaskRequest request) {

        Task task = getTaskById(id); 

        task.setTitle(request.getTitle()); 
        task.setDescription(request.getDescription());
        task.setCompleted(request.isCompleted());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id); 

        taskRepository.delete(task);
    }


}