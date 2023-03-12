package com.netgroup.usecase.inventory_system.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ItemDto {
    private Long id;
    @NonNull
    private String name;
    private Long storageId;
    @Builder.Default
    private String type = "ITEM";
}
