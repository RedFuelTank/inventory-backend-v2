package com.netgroup.usecase.inventory_system.api;

import com.netgroup.entity.inventory_system.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepository {
    Page<Item> getStorageItemsBy(Long storageId, String businessName, Pageable pageable);

    Item saveItem(Item item);

    Item deleteItem(Long id, String businessName);

    Page<Item> findItemsBy(String name, String businessName, Pageable pageable);

    Item getItemById(Long id);
}
