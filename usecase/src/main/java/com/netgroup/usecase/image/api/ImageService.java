package com.netgroup.usecase.image.api;

public interface ImageService {
    void uploadImage(Long itemId, String businessName, byte[] data);

    ImageDto getImageBy(Long itemId);
}
