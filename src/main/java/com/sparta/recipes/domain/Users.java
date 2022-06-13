package com.sparta.recipes.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "USERS_SEQ_GENERATOR",
        sequenceName = "USERS_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Users {

    /*GenerationType.SEQUENCE 테이블별로 Id값을 따로따로 1,2,3...순서대로 부여*/
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "USERS_SEQ_GENERATOR")
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    public Users(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
