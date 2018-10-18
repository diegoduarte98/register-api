package br.com.registerapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.registerapi.model.Phone;
import br.com.registerapi.model.User;
import br.com.registerapi.repository.UserRepository;
import br.com.registerapi.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceConfigurationTest {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        when(userRepository.findAll()).thenReturn(new ArrayList<>());
    }

    @Test
    public void deve_salvar_um_usuario_no_banco() {

        List<Phone> phones = new ArrayList<>();
        phones.add(Phone.builder().ddd(11).number(957995397).build());

        User user = User.builder()
                .email("diego@diegoduarte.com.br")
                .name("Diego")
                .password("123456")
                .phones(phones)
                .build();

        when(userRepository.save(user)).thenReturn(user);

        assertThat(userService.create(user)).isNotNull();
        assertThat(userService.create(user).getName()).isEqualTo("Diego");
    }

    @Test
    public void deve_retornar_erro_campo_obrigatorio() {
        User user = User.builder().build();

        when(userRepository.save(user)).thenReturn(user);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(3).isEqualTo(violations.size());
    }
}
