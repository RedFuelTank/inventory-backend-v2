package com.netgroup.usecase.inventory_system.api;

import com.netgroup.entity.inventory_system.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getStorageItemsBy(int storageId, String businessName);
}
