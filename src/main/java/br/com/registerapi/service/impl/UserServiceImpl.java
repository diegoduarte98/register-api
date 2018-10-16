package br.com.registerapi.service.impl;

import br.com.registerapi.dto.NewUserDTO;
import br.com.registerapi.dto.UserSavedDTO;
import br.com.registerapi.model.User;
import br.com.registerapi.repository.UserRepository;
import br.com.registerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserSavedDTO create(NewUserDTO newUser) {

        User user = User.builder()
            .name(newUser.getName())
            .email(newUser.getEmail())
            .password(newUser.getPassword())
            .phones(newUser.getPhones())
            .created(LocalDate.now())
            .modified(LocalDate.now())
        .build();

        User save = userRepository.save(user);

        UserSavedDTO userSaved = UserSavedDTO.builder()
            .id(save.getId())
            .name(save.getName())
            .email(save.getEmail())
            .phones(save.getPhones()).

        return userSaved;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
