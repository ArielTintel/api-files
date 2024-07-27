package com.arieltintel.files.repository;

import com.arieltintel.files.entity.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, UUID> {
    Optional<ProfilePhoto> findByUserId(UUID userId);
}
