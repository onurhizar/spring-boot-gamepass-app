package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.entity.FileInfo;
import com.onurhizar.gamepass.service.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileUploadController {

    private FileUploadService fileUploadService;

    @GetMapping
    public List<FileInfo> listFiles(){
        return fileUploadService.listFiles();
    }

    @PostMapping("upload")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        String fileId = fileUploadService.storeFile(file);
        return "file uploaded with id="+fileId;
    }

    @GetMapping("download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId){
        return fileUploadService.downloadFile(fileId);
    }
}
