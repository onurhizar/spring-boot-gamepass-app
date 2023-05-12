package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, String> {

}
