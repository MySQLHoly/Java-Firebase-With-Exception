package com.sandro.postmanagement.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {
    private String id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacio")
    private String lastname;

    @NotBlank(message = "El email no puede estar vacio")
    private String email;
}
