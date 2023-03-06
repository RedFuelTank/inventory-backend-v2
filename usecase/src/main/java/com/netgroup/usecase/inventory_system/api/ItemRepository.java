package com.netgroup.usecase.inventory_system.api;

import com.netgroup.entity.inventory_system.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> getStorageItemsBy(Optional<Long> storageId, String businessName);

    Item saveItem(Item item);
}
