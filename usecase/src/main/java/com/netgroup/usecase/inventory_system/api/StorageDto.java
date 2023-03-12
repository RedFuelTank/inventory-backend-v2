package com.netgroup.usecase.inventory_system.api;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageDto {
    private Long id;
    @NonNull
    private String name;
    private Long upperStorageId;
    private final String type = "STORAGE";
}
