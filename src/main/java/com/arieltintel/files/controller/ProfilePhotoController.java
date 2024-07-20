package com.arieltintel.files.controller;

import com.arieltintel.files.service.ProfilePhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile-photos")
public class ProfilePhotoController {

    private final ProfilePhotoService profilePhotoService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return profilePhotoService.uploadProfilePhoto(file);
    }

    @GetMapping("/download/{key}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String key) {
        return profilePhotoService.downloadProfilePhoto(key);
    }

}