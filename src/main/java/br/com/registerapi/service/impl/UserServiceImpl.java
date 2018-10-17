package br.com.registerapi.service.impl;

import br.com.registerapi.exception.EmailException;
import br.com.registerapi.model.Phone;
import br.com.registerapi.model.User;
import br.com.registerapi.repository.UserRepository;
import br.com.registerapi.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static br.com.registerapi.security.SecurityConstants.EXPIRATION_TIME;
import static br.com.registerapi.security.SecurityConstants.SECRET;

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
            throw new EmailException("E-mail já existente.");
        }

        User userSaved = userRepository.save(this.getUserBuilder(user));

        List<Phone> phones = userSaved.getPhones();
        phones.forEach(phone -> phone.setUserId(userSaved.getId()));

        return userSaved;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private User getUserBuilder(User user) {

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();

        User newUser = user;
        newUser.setCreated(LocalDate.now());
        newUser.setModified(LocalDate.now());
        newUser.setLastLogin(LocalDate.now());
        newUser.setToken(token);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        return newUser;
    }
}
