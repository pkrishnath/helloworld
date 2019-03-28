package com.revolut.hometask.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "^(\\d{4}-\\d{2}-\\d{2})$")
    private String dateOfBirth;

    private String message;

}
