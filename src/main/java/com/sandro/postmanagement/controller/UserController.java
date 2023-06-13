package com.sandro.postmanagement.controller;

import com.sandro.postmanagement.dto.ErrorDTO;
import com.sandro.postmanagement.dto.UserDTO;
import com.sandro.postmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@Valid @RequestBody UserDTO userDTO) {
        userService.add(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Boolean.TRUE);
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserDTO>> queryAll() throws ExecutionException, InterruptedException {
        List<UserDTO> listUsers = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(listUsers);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Boolean> edit(@PathVariable String id, @RequestBody UserDTO userDTO) throws ExecutionException, InterruptedException {
        userService.edit(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> delete(@PathVariable String id) throws ExecutionException, InterruptedException{
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
