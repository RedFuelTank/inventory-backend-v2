package com.netgroup.usecase.inventory_system.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StorageDto {
    private Long id;
    @NonNull
    private String name;
    private Long upperStorageId;
    @Builder.Default
    private String type = "STORAGE";
}
