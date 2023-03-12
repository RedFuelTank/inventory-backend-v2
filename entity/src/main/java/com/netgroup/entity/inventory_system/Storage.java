package com.netgroup.entity.inventory_system;

import lombok.*;

@Builder
@Getter
@Setter
public class Storage {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String businessName;
    private Long upperStorageId;
}
