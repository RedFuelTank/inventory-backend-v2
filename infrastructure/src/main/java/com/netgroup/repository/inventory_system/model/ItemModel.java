package com.netgroup.repository.inventory_system.model;

import com.netgroup.entity.inventory_system.Storage;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class ItemModel {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "storage_id")
    private Long storageId;

    @Column(name = "business_name")
    private String businessName;
}
