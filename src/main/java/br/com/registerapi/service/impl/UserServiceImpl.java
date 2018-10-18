package br.com.registerapi.service.impl;

import br.com.registerapi.dto.CredenciaisDTO;
import br.com.registerapi.exception.EmailExistingException;
import br.com.registerapi.exception.EmailNotFoundException;
import br.com.registerapi.exception.PasswordInvalidException;
import br.com.registerapi.exception.SessionInvalidException;
import br.com.registerapi.model.User;
import br.com.registerapi.repository.UserRepository;
import br.com.registerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User create(User user) {

        Optional<User> emailOptional = userRepository.findByEmail(user.getEmail());

        if(emailOptional.isPresent()) {
            throw new EmailExistingException();
        }

        User userSaved = userRepository.save(user.newUser(bCryptPasswordEncoder));

        return userSaved;
    }

    @Override
    public User auth(CredenciaisDTO credenciais) {

        Optional<User> emailOptional = userRepository.findByEmail(credenciais.getEmail());

        emailOptional.filter(user -> user.getEmail().equals(credenciais.getEmail())).orElseThrow(EmailNotFoundException::new);
        emailOptional.filter(user -> bCryptPasswordEncoder.matches(credenciais.getPassword(), user.getPassword())).orElseThrow(PasswordInvalidException::new);

        User user = emailOptional.get();
        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }

    @Override
    public User findById(UUID id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) {
            throw new NoResultException();
        }

        optionalUser.filter(user -> Duration.between(user.getLastLogin(), LocalDateTime.now()).toMinutes() < 30).orElseThrow(SessionInvalidException::new);

        return optionalUser.get();
    }
}
