package com.arieltintel.files.service;

import com.arieltintel.files.client.BucketClient;
import com.arieltintel.files.entity.ProfilePhoto;
import com.arieltintel.files.enums.FileTypeEnum;
import com.arieltintel.files.repository.ProfilePhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfilePhotoService {

    private final BucketClient bucketClient;
    private final ProfilePhotoRepository profilePhotoRepository;

    @Value("${bucket.profile-photos}")
    private String bucketProfilePhotos;

    public String uploadProfilePhoto(MultipartFile file, UUID userId) throws IOException {
        String prefixKey = FileTypeEnum.USER_PROFILE.name();
        String key = prefixKey + "/" + userId + "/" + UUID.randomUUID() + "/" + file.getOriginalFilename();
        bucketClient.upload(file, key, bucketProfilePhotos);

        Optional<ProfilePhoto> profilePhotoOptional = profilePhotoRepository.findByUserId(userId);
        if (profilePhotoOptional.isPresent()) {
            profilePhotoOptional.get().setProfileImagePath(key);
            profilePhotoRepository.save(profilePhotoOptional.get());
        } else {
            ProfilePhoto profilePhoto = ProfilePhoto.builder()
                    .userId(userId)
                    .fileTypeEnum(FileTypeEnum.USER_PROFILE)
                    .profileImagePath(key)
                    .build();
            profilePhotoRepository.save(profilePhoto);
        }

        return "File uploaded: " + key;
    }

    public ResponseEntity<byte[]> downloadProfilePhoto(UUID userId) {
        ProfilePhoto profilePhoto = profilePhotoRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        String profileImagePath = profilePhoto.getProfileImagePath();
        byte[] content = bucketClient.download(profileImagePath, bucketProfilePhotos);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (profileImagePath.endsWith(".jpg") || profileImagePath.endsWith(".jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (profileImagePath.endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (profileImagePath.endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(content);
    }

}
