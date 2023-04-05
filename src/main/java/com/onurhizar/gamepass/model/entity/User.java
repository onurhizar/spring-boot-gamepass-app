package com.onurhizar.gamepass.model.entity;

import com.onurhizar.gamepass.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String surname;
    private String email;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.GUEST;

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
        return List.of(new SimpleGrantedAuthority(role.name()));
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
