package br.com.registerapi.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomErrorDetails {

    private String message;

}
