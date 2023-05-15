package com.onurhizar.gamepass.model.entity;

import com.onurhizar.gamepass.model.enums.UserRole;
import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    private String name;
    private String surname;
    private String email;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.GUEST;

    @Builder.Default
    private boolean verified = false;

    @Builder.Default
    private String verificationCode = UUID.randomUUID().toString();
    @Builder.Default
    private ZonedDateTime verificationCodeExpireDate = ZonedDateTime.now().plusDays(1); // TODO fixed value?

    private String recoveryCode;
    private ZonedDateTime recoveryCodeExpireDate;

    @ManyToMany
    @JoinTable(name = "users_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    @Builder.Default
    private List<Game> favoriteGames = new LinkedList<>();

    @ManyToMany
    @JoinTable(name = "users_categories",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private List<Category> followedCategories = new LinkedList<>();

    @OneToOne(mappedBy = "user")
    private ContractRecord contractRecord;


    // below are for security package
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
