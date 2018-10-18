package br.com.registerapi.service;

import br.com.registerapi.dto.CredenciaisDTO;
import br.com.registerapi.model.User;

import java.util.UUID;

public interface UserService {

    User create(User user);

    User auth(CredenciaisDTO credenciais);

    User findById(UUID id);
}
