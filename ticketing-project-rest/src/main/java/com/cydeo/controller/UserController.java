package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/V1/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("successfully retrieved ", userDTOList, HttpStatus.OK ));
    }
    @GetMapping("/{username}")
    public  ResponseEntity<ResponseWrapper> getUserByName(@PathVariable String username){
        UserDTO byUserName = userService.findByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("successfully retrieved", byUserName,HttpStatus.OK));
    }
    @PostMapping
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is successfully created",HttpStatus.CREATED));

    }
    @PutMapping("{id}")
    public ResponseEntity<ResponseWrapper> updateUser(@PathVariable String id, @RequestBody UserDTO user){
        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("successfully updated ",user,HttpStatus.ACCEPTED));
    }

    @DeleteMapping("{username}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable String username){
        userService.deleteByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("user is deleted",HttpStatus.OK));
    }
}
