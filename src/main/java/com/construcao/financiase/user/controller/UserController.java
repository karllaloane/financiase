package com.construcao.financiase.user.controller;

import com.construcao.financiase.user.dto.MessageDTO;
import com.construcao.financiase.user.dto.UserDTO;
import com.construcao.financiase.user.service.AuthenticationService;
import com.construcao.financiase.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO){
//        return userService.create(userToCreateDTO);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO register(@RequestBody @Valid UserDTO userToCreateDTO) {
        return userService.register(userToCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userToUpdateDTO) {
        return userService.update(id, userToUpdateDTO);
    }

}
