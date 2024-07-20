package com.arieltintel.files.service;

import com.arieltintel.files.client.BucketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfilePhotoService {

    private final BucketClient bucketClient;

    @Value("${bucket.profile-photos}")
    private String bucketProfilePhotos;

    public String uploadProfilePhoto(MultipartFile file) throws IOException {
        String prefixKey = UUID.randomUUID().toString(); //TODO - Utilizar o ID de um recurso PK
        String key = prefixKey + "_" + file.getOriginalFilename();

        bucketClient.upload(file, key, bucketProfilePhotos);

        return "File uploaded: " + key;
    }

    //TODO - Inserir base de dados para recuperar a imagem
    public ResponseEntity<byte[]> downloadProfilePhoto(String key) {
        byte[] content = bucketClient.download(key, bucketProfilePhotos);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (key.endsWith(".jpg") || key.endsWith(".jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (key.endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (key.endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(content);
    }

}
