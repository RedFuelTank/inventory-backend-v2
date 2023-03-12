package com.netgroup.repository.image;

import com.netgroup.entity.image.Image;
import com.netgroup.repository.image.model.ImageModel;
import com.netgroup.usecase.image.api.ImageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepositoryImpl extends ImageRepository, JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findImageModelById(Long id);
    @Override
    default void uploadImage(Image image) {
        save(ImageModel.builder()
                .id(image.getItemId())
                .businessName(image.getBusinessName())
                .data(image.getData())
                .build());
    }

    @Override
    default Optional<Image> getImageBy(Long itemId) {
        Optional<ImageModel> possibleImageModel = findImageModelById(itemId);

        if (possibleImageModel.isPresent()) {
            ImageModel imageModel = possibleImageModel.get();
            return Optional.of(Image.builder()
                    .itemId(imageModel.getId())
                    .businessName(imageModel.getBusinessName())
                    .data(imageModel.getData())
                    .build());
        } else {
            return Optional.empty();
        }

    };
}
