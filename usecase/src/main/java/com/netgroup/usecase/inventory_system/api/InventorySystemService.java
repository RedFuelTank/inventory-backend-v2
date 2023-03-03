package com.netgroup.usecase.inventory_system.api;

import java.util.List;

public interface InventorySystemService {
    List<ItemDto> getStorageItemsBy(int storageId, String businessName);

    List<StorageDto> getSubStoragesBy(int storageId, String businessName);

}
