package com.netgroup.usecase.inventory_system.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InventorySystemService {
    Page<ItemDto> getStorageItemsBy(Optional<Long> storageId, String businessName, Pageable pageable);

    Page<StorageDto> getSubStoragesBy(Optional<Long> storageId, String businessName, Pageable pageable);

    StorageDto saveStorage(StorageDto storageDto, String businessName);

    ItemDto saveItem(ItemDto itemDto, String businessName);

    ItemDto deleteItem(Long id, String businessName);

    StorageDto deleteStorage(Long id, String businessName);

    Page<ItemDto> findItemsBy(String name, String businessName, Pageable pageable);

    Page<StorageDto> findStoragesBy(String name, String businessName, Pageable pageable);
}
