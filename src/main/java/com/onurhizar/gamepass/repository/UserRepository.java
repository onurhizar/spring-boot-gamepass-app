package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);

    public User findUserByVerificationCode(String verificationCode);

    public User findUserByRecoveryCode(String recoveryCode);

}
