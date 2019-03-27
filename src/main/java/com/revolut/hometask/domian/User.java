package com.revolut.hometask.domian;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A User.
 */
@Entity
@Table(name = "user")
@Data
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$")
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Pattern(regexp = "^(\\d{4}-\\d{2}-\\d{2})$")
    @Column(name = "dob", nullable = false)
    private String dob;
}
