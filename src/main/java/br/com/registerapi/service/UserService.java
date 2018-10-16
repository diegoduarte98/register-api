package br.com.registerapi.service;

import br.com.registerapi.dto.NewUserDTO;
import br.com.registerapi.dto.UserSavedDTO;
import br.com.registerapi.model.User;

import java.util.List;

public interface UserService {

    UserSavedDTO create(NewUserDTO newUser);

    List<User> findAll();
}
