package com.arieltintel.files.entity;

import com.arieltintel.files.enums.FileTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProfilePhoto {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileTypeEnum fileTypeEnum;

    @Column(nullable = false)
    private String profileImagePath;

}