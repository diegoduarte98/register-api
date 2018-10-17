package br.com.registerapi.controller;

import br.com.registerapi.model.User;
import br.com.registerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody @Valid User user) {
        return userService.create(user);
    }

    @GetMapping
    public List<User> get() {
        return userService.findAll();
    }

}

