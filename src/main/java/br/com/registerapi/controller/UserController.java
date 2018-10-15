package br.com.registerapi.controller;

import br.com.registerapi.dto.UserDTO;
import br.com.registerapi.model.User;
import br.com.registerapi.repository.UserRepository;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public UserDTO create(@RequestBody @Valid User user) {
        User userSave = userRepository.save(user);
        return null;
    }

    @GetMapping
    public List<User> get() {

        List<User> users = userRepository.findAll();


        return users;
    }

}

