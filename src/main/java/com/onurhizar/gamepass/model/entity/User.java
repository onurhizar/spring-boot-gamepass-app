package com.onurhizar.gamepass.model.entity;

import com.onurhizar.gamepass.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String surname;
    private String email;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.GUEST;

    private boolean verified = false;
}
