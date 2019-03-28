package com.revolut.hometask.dao;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A User.
 */
@Entity
@Table(name = "user")
@Data
public class UserDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$")
    @Column(name = "username", length=10, nullable = false)
    private String username;

    @NotNull
    @Column(name = "dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;
}
