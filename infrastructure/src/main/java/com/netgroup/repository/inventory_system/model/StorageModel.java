package com.netgroup.repository.inventory_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "storages")
public class StorageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "upper_storage_id")
    private Long upperStorageId;

    @Column(name = "business_name")
    private String businessName;
}
