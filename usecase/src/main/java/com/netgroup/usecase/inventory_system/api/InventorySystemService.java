package com.netgroup.usecase.inventory_system.api;

import java.util.List;
import java.util.Optional;

public interface InventorySystemService {
    List<ItemDto> getStorageItemsBy(Optional<Long> storageId, String businessName);

    List<StorageDto> getSubStoragesBy(Optional<Long> storageId, String businessName);

    StorageDto saveStorage(StorageDto storageDto, String businessName);

    ItemDto saveItem(ItemDto itemDto, String businessName);

}
