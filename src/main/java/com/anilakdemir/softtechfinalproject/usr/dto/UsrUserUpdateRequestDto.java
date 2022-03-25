package com.anilakdemir.softtechfinalproject.usr.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author anilakdemir
 */
@Data
public class UsrUserUpdateRequestDto {

    @NotNull(message = "ID can not be null")
    private Long id;

    @NotNull(message = "Name can not be null")
    private String name;

    @NotNull(message = "Surname can not be null")
    private String surname;

    @NotNull(message = "Username can not be null")
    private String username;

    @NotNull(message = "Password can not be null")
    private String password;
}
