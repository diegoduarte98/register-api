package br.com.registerapi.dto;

import br.com.registerapi.model.Phone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class NewUserDTO {

    private String name;

    private String email;

    private String password;

    private List<Phone> phones;

}
