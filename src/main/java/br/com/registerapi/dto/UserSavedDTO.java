package br.com.registerapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class UserSavedDTO {

    @JsonProperty("last_login")
    private LocalDate lastLogin;

    private String token;
}
