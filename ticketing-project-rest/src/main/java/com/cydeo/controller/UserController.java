package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/V1/user")
@Tag(name = "UserController", description = "USER API")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Get all users")
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("successfully retrieved ", userDTOList, HttpStatus.OK ));
    }
    @GetMapping("/{username}")
    @RolesAllowed("Admin")
    @Operation(summary = "Get user by username")
    public  ResponseEntity<ResponseWrapper> getUserByName(@PathVariable String username){
        UserDTO byUserName = userService.findByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("successfully retrieved", byUserName,HttpStatus.OK));
    }
    @PostMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Create a new user")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is successfully created",user,HttpStatus.CREATED));

    }
    @PutMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Update user")
    public ResponseEntity<ResponseWrapper> updateUser( @RequestBody UserDTO user){
        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("successfully updated ",user,HttpStatus.ACCEPTED));
    }

    @DeleteMapping("/{username}")
    @RolesAllowed("Admin")
    @Operation(summary = "delete user by username")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable String username){
        userService.deleteByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("user is deleted",HttpStatus.OK));
    }
}
