package com.netgroup.usecase.inventory_system.api;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    @NonNull
    private String name;
    private Long storageId;
    private final String type = "ITEM";
}
