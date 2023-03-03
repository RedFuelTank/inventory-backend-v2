package com.netgroup.entity.inventory_system;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Storage {
    private Long id;
    @NonNull
    private String name;
    private Long upperStorageId;
}
