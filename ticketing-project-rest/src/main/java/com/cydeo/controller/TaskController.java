package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getAllTasks(){
       return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskService.listAllTasks(), HttpStatus.OK));
    }

    @GetMapping("/{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable Long id){
        TaskDTO byId = taskService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved",byId, HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> retrieveEmployeePendingTask(){
        taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> retrieveEmployeeArchiveTask(){
        List<TaskDTO> taskDTOS = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOS, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity.accepted().body(new ResponseWrapper("Task is successfully created", HttpStatus.ACCEPTED));
    }
    @DeleteMapping("/{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted", HttpStatus.OK));
    }
    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", HttpStatus.OK));
    }
    @PutMapping("/employee/update/")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTask(@RequestBody TaskDTO taskDTO){
        taskService.updateStatus(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", HttpStatus.OK));
    }
}
