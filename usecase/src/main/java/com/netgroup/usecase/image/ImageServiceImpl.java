package com.netgroup.usecase.image;

import com.netgroup.entity.image.Image;
import com.netgroup.usecase.image.api.ImageDto;
import com.netgroup.usecase.image.api.ImageRepository;
import com.netgroup.usecase.image.api.ImageService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;
    @Override
    public void uploadImage(Long itemId, String businessName, byte[] data) {
        repository.uploadImage(Image.builder()
                .itemId(itemId)
                .businessName(businessName)
                .data(data)
                .build());
    }

    @Override
    public ImageDto getImageBy(Long itemId) {
        Optional<Image> possibleImage = repository.getImageBy(itemId);

        if (possibleImage.isPresent()) {
            Image image = possibleImage.get();
            return ImageDto.builder()
                    .itemId(image.getItemId())
                    .businessName(image.getBusinessName())
                    .data(image.getData())
                    .build();
        } else {
            return null;
        }
    }
}
