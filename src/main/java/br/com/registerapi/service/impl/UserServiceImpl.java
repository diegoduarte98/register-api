package br.com.registerapi.service.impl;

import br.com.registerapi.model.Phone;
import br.com.registerapi.model.User;
import br.com.registerapi.repository.UserRepository;
import br.com.registerapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User create(User user) {

        user.setCreated(LocalDate.now());
        user.setModified(LocalDate.now());
        user.setLastLogin(LocalDate.now());
        user.setToken("sdsa");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);

        List<Phone> phones = userSaved.getPhones();
        phones.forEach(phone -> phone.setUserId(userSaved.getId()));

        return userSaved;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
