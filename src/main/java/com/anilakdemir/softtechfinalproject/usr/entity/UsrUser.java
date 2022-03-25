package com.anilakdemir.softtechfinalproject.usr.entity;

import com.anilakdemir.softtechfinalproject.gen.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author anilakdemir
 */
@Entity
@Table(name = "USR_USER")
@Data
public class UsrUser extends BaseEntity {

    @Id
    @SequenceGenerator(name = "UsrUser", sequenceName = "USR_USER_ID_SEQ")
    @GeneratedValue(generator = "UsrUser")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USERNAME", nullable = false)
    private String username;

}
