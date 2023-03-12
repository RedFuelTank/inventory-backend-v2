package com.netgroup.usecase.image.api;

import com.netgroup.entity.image.Image;

import java.util.Optional;

public interface ImageRepository {
    void uploadImage(Image image);

    Optional<Image> getImageBy(Long itemId);
}
