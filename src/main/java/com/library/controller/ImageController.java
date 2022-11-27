package com.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/imagedata";


    @PostMapping("upload-file")
    @PreAuthorize("hasRole('ADMIN')")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        StringBuilder fileNames = new StringBuilder();
        String filename = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, filename);

        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return "Successfully Image is uploaded";
    }


}
