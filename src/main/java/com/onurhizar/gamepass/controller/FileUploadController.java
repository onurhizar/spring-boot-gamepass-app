package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.service.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileUploadController {

    private FileUploadService fileUploadService;

    @PostMapping("upload")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        String fileId = fileUploadService.storeFile(file);
        return "file uploaded with id="+fileId;
    }
}
