package br.com.registerapi.dto;

import br.com.registerapi.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserSavedDTO extends User {

    @JsonProperty("last_login")
    private LocalDate lastLogin;

    private String token;
}
