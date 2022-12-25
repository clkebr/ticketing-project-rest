package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    @RolesAllowed({"Admin","Manager"})
    public ResponseEntity<ResponseWrapper> getAllProject(){
        List<ProjectDTO> projectDTOS = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",projectDTOS, HttpStatus.OK));

    }

    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable String projectCode){
        ProjectDTO projectDTO = projectService.getByProjectCode(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved",projectDTO, HttpStatus.OK));

    }

    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getAllProjectByManager(){
        List<ProjectDTO> projectDTOS = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("projects are successfully retrieved",projectDTOS, HttpStatus.OK));

    }

    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO){
         projectService.save(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("projects are successfully created",HttpStatus.CREATED));
    }

    @DeleteMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable String projectCode){
        projectService.delete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Projects is successfully deleted",HttpStatus.OK));
    }

    @PutMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@PathVariable String projectCode,@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully updated",projectDTO,HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> completeProject(@PathVariable String projectCode){
        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("projects are successfully updated",HttpStatus.OK));
    }

}
