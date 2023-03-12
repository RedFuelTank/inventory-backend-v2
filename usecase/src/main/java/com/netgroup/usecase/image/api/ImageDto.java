package com.netgroup.usecase.image.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ImageDto {
    @NonNull
    private Long itemId;
    private byte[] data;
    @NonNull
    private String businessName;
}
