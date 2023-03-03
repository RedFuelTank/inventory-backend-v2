package com.netgroup.usecase.inventory_system.api;

import com.netgroup.entity.inventory_system.Storage;

import java.util.List;

public interface StorageRepository {
    List<Storage> getSubStoragesBy(int storageId, String businessName);
}
