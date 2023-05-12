package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.FileInfo;
import com.onurhizar.gamepass.repository.FileInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FileInfoRepository repository;

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

        FileInfo newFileInfo = FileInfo.builder()
            .name(file.getOriginalFilename())
            .contentType(file.getContentType())
            .byteSize(file.getSize())
            .url(filePath.toString())
            .build();

        FileInfo uploadedFileInfo = repository.save(newFileInfo);
        return uploadedFileInfo.getId();
    }

    public ResponseEntity<Resource> downloadFile(String fileId) {
        FileInfo savedFile = repository.findById(fileId).orElseThrow(EntityNotFoundException::new);
        String absolutePath = savedFile.getUrl();

        File file = new File(absolutePath);
        Resource resource = new FileSystemResource(file);

        if (!resource.exists()) throw new EntityNotFoundException();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public List<FileInfo> listFiles() {
        return repository.findAll();
    }
}
