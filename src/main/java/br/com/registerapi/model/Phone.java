package br.com.registerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@EqualsAndHashCode
public class Phone {

    @Id
    @JsonIgnore
    private Long id;

    private Integer ddd;

    private Integer number;

    @JsonIgnore
    private Long userId;

}
