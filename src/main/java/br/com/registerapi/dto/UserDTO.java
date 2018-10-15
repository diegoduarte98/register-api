package br.com.registerapi.dto;

import br.com.registerapi.model.Phone;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private LocalDate created;

    private LocalDate modified;

    @JsonProperty("last_login")
    private LocalDate lastLogin;

    private String token;

    private List<Phone> phones;

}
