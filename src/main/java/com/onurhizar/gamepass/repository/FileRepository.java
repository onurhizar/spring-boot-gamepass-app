package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {

}
