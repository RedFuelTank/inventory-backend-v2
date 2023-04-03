package com.netgroup.web.inventory_system;

import com.netgroup.usecase.image.api.ImageDto;
import com.netgroup.usecase.image.api.ImageService;
import com.netgroup.usecase.inventory_system.api.InventorySystemService;
import com.netgroup.usecase.inventory_system.api.ItemDto;
import com.netgroup.usecase.inventory_system.api.StorageDto;
import com.netgroup.usecase.payment.api.PaymentService;
import com.netgroup.usecase.statistics.api.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business")
public class InventorySystemController {
    private final InventorySystemService inventoryService;
    private final PaymentService paymentService;
    private final StatisticsService statisticsService;
    private final ImageService imageService;

    @GetMapping("/storage")
    public Page<Object> getStorageContent(@RequestParam(name = "storageId", required = false) Optional<Long> possibleUpperStorageId, Authentication auth, Pageable pageable) {
        double storageSize = Math.ceil(pageable.getPageSize() / 2d);
        double itemSize = Math.floor(pageable.getPageSize() / 2d);

        Page<ItemDto> storageItems = inventoryService.getStorageItemsBy(
                possibleUpperStorageId,
                auth.getName(),
                PageRequest.of(pageable.getPageNumber(), (int) itemSize)
        );
        Page<StorageDto> subStorages = inventoryService.getSubStoragesBy(
                possibleUpperStorageId,
                auth.getName(),
                PageRequest.of(pageable.getPageNumber(), (int) storageSize));

        List<Object> objects = Stream.concat(
                subStorages.getContent().stream(),
                storageItems.getContent().stream()
        ).toList();

        return new PageImpl<>(objects, pageable, storageItems.getTotalElements() + subStorages.getTotalElements());
    }

    @GetMapping("/search")
    public Page<Object> getContentBy(@RequestParam String name, Authentication auth, Pageable pageable) {
        double storageSize = Math.ceil(pageable.getPageSize() / 2d);
        double itemSize = Math.floor(pageable.getPageSize() / 2d);

        Page<ItemDto> storageItems = inventoryService.findItemsBy(
                name,
                auth.getName(),
                PageRequest.of(pageable.getPageNumber(), (int) itemSize)
        );
        Page<StorageDto> subStorages = inventoryService.findStoragesBy(
                name,
                auth.getName(),
                PageRequest.of(pageable.getPageNumber(), (int) storageSize)
        );

        List<Object> objects = Stream.concat(
                subStorages.getContent().stream(),
                storageItems.getContent().stream()
        ).toList();

        return new PageImpl<>(objects, pageable, storageItems.getTotalElements() + subStorages.getTotalElements());
    }

    @GetMapping("/item/{id}")
    public ItemDto getItemById(@PathVariable Long id) {
        return inventoryService.getItemById(id);
    }

    @PostMapping("/storage")
    public StorageDto addStorage(@RequestBody StorageDto storage, Authentication auth) {
        return inventoryService.saveStorage(storage, auth.getName());
    }

    @PostMapping("/item")
    public ItemDto addItem(@RequestBody ItemDto item, Authentication auth) {
        ItemDto itemDto = inventoryService.saveItem(item, auth.getName());
        paymentService.incrementTotalAmount(auth.getName());
        statisticsService.addItemToStatistics(itemDto, auth.getName());

        return itemDto;
    }

    @DeleteMapping("/item/{id}")
    public ItemDto deleteItem(@PathVariable Long id, Authentication auth) {
        statisticsService.setItemOutdated(id, auth.getName());
        ItemDto itemDto = inventoryService.deleteItem(id, auth.getName());
        paymentService.decrementTotalAmount(auth.getName());

        return itemDto;
    }

    @DeleteMapping("/storage/{id}")
    public StorageDto deleteStorage(@PathVariable Long id, Authentication auth) {
        return inventoryService.deleteStorage(id, auth.getName());
    }

    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam(name = "files") MultipartFile files, Authentication auth) {
        try {
            imageService.uploadImage(0L, auth.getName(), files.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Uploaded";
    }

    @GetMapping(value = "/load/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] loadImage(@PathVariable Long id, Authentication authentication) {
        ImageDto image = imageService.getImageBy(id);

        if (image == null) {
            throw new IllegalArgumentException();
        }

        return image.getData();
    }
}
