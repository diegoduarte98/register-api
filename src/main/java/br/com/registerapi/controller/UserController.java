package br.com.registerapi.controller;

import br.com.registerapi.dto.CredenciaisDTO;
import br.com.registerapi.model.User;
import br.com.registerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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

    @PostMapping("auth")
    public User auth(@RequestBody @Valid CredenciaisDTO credenciais) {
        return userService.auth(credenciais);
    }

    @GetMapping("{id}")
    public User findById(@PathVariable("id") UUID id) {
        return userService.findById(id);
    }

}

