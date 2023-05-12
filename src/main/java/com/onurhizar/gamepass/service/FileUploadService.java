package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.File;
import com.onurhizar.gamepass.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FileRepository repository;

    @Value("${upload.directory}")
    private String uploadDirectory;


    /** Stores the file in the upload directory and returns the id of the file */
    public String storeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) fileName = "null";
        Path filePath = Path.of(uploadDirectory).resolve(fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        File newFile = File.builder()
            .name(file.getOriginalFilename())
            .contentType(file.getContentType())
            .byteSize(file.getSize())
            .url(filePath.toString())
            .build();

        File uploadedFile = repository.save(newFile);
        return uploadedFile.getId();
    }
}
