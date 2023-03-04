package com.netgroup.entity.inventory_system;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Item {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String businessName;
    private Long storageId;
}
