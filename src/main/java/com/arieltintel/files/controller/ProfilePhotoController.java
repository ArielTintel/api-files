package com.arieltintel.files.controller;

import com.arieltintel.files.service.ProfilePhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile-photos")
public class ProfilePhotoController {

    private final ProfilePhotoService profilePhotoService;

    @PostMapping("/upload/user/{userId}")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @PathVariable("userId") UUID userId) throws IOException {
        return profilePhotoService.uploadProfilePhoto(file, userId);
    }

    @GetMapping("/download/user/{userId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("userId") UUID userId) {
        return profilePhotoService.downloadProfilePhoto(userId);
    }

}