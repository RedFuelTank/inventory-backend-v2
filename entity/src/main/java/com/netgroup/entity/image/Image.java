package com.netgroup.entity.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Image {
    @NonNull
    private Long itemId;
    private byte[] data;
    @NonNull
    private String businessName;
}
