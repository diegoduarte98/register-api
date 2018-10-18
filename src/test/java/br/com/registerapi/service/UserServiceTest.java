package br.com.registerapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.registerapi.dto.CredenciaisDTO;
import br.com.registerapi.exception.EmailNotFoundException;
import br.com.registerapi.exception.PasswordInvalidException;
import br.com.registerapi.exception.SessionInvalidException;
import br.com.registerapi.model.Phone;
import br.com.registerapi.model.User;
import br.com.registerapi.repository.UserRepository;
import br.com.registerapi.service.impl.UserServiceImpl;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.*;

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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Validator validator;

    private User user;

    private List<Phone> phones;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        phones = new ArrayList<>();
        phones.add(Phone.builder().ddd(11).number(957995397).build());

        user = User.builder()
                .email("diego@diegoduarte.com.br")
                .name("Diego")
                .password("123456")
                .phones(phones)
                .build();

        when(userRepository.findAll()).thenReturn(new ArrayList<>());
    }

    @Test
    public void deve_salvar_um_usuario_no_banco() {
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

    @Test
    public void deve_autenticar_usuario() {
        CredenciaisDTO credenciais = CredenciaisDTO.builder()
                .email("diego@diegoduarte.com.br")
                .password("123456")
                .build();

        when(userRepository.save(user.newUser(bCryptPasswordEncoder))).thenReturn(user);
        when(userRepository.findByEmail(credenciais.getEmail())).thenReturn(Optional.of(user));

        assertThat(userService.auth(credenciais).getName()).isEqualTo("Diego");

        verify(userRepository).save(user);
    }

    @Test
    public void deve_retornar_erro_de_senha() {

        expectedException.expect(PasswordInvalidException.class);

        CredenciaisDTO credenciais = CredenciaisDTO.builder()
                .email("diego@diegoduarte.com.br")
                .password("99999")
                .build();

        when(userRepository.save(user.newUser(bCryptPasswordEncoder))).thenReturn(user);
        when(userRepository.findByEmail(credenciais.getEmail())).thenReturn(Optional.of(user));

        userService.auth(credenciais);
    }

    @Test
    public void deve_retornar_erro_de_email() {

        expectedException.expect(EmailNotFoundException.class);

        CredenciaisDTO credenciais = CredenciaisDTO.builder()
                .email("diego@diegodusarte.com.br")
                .password("123456")
                .build();

        when(userRepository.save(user.newUser(bCryptPasswordEncoder))).thenReturn(user);
        when(userRepository.findByEmail(credenciais.getEmail())).thenReturn(Optional.of(user));

        userService.auth(credenciais);
    }

    @Test
    public void deve_retornar_usuario_encontrado() {

        when(userRepository.save(user.newUser(bCryptPasswordEncoder))).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThat(userService.findById(user.getId()).getName()).isEqualTo("Diego");
    }

    @Test
    public void deve_retornar_usuario_nao_encontrado() {
        expectedException.expect(NoResultException.class);

        when(userRepository.save(user.newUser(bCryptPasswordEncoder))).thenReturn(user);
        when(userRepository.findById(UUID.randomUUID())).thenReturn(Optional.of(user));

        userService.findById(user.getId());
    }

    @Test
    public void deve_retornar_session_expirada() {
        expectedException.expect(SessionInvalidException.class);

        when(userRepository.save(user.newUser(bCryptPasswordEncoder))).thenReturn(user);

        user.setLastLogin(LocalDateTime.now().minusMinutes(30));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.findById(user.getId());
    }
}
