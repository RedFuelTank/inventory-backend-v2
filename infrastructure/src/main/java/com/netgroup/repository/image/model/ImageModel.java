package com.netgroup.repository.image.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class ImageModel {
    @Id
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "business_name")
    private String businessName;
}
