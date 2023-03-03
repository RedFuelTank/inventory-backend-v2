package com.netgroup.usecase.inventory_system;

import com.netgroup.usecase.inventory_system.api.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InventorySystemServiceImpl implements InventorySystemService {
    private final StorageRepository storageRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemDto> getStorageItemsBy(int storageId, String businessName) {
        itemRepository.getStorageItemsBy(storageId, businessName);
        return null;
    }

    @Override
    public List<StorageDto> getSubStoragesBy(int storageId, String businessName) {
        storageRepository.getSubStoragesBy(storageId, businessName);
        return null;
    }
}
