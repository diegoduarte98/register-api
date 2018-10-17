package br.com.registerapi.service;

import br.com.registerapi.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    List<User> findAll();
}
