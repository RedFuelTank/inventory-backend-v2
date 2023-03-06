package com.netgroup.usecase.inventory_system.api;

import com.netgroup.entity.inventory_system.Storage;

import java.util.List;
import java.util.Optional;

public interface StorageRepository {
    List<Storage> getSubStoragesBy(Optional<Long> storageId, String businessName);

    Storage saveStorage(Storage storage);
}
