package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);

    public User findUserByVerificationCode(String verificationCode);

    public User findUserByRecoveryCode(String recoveryCode);

    public List<User> findByFollowedCategories(Category category);

}
