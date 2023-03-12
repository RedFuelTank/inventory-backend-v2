package com.netgroup.usecase.inventory_system.api;

import com.netgroup.entity.inventory_system.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StorageRepository {
    Page<Storage> getSubStoragesBy(Long storageId, String businessName, Pageable pageable);

    Storage saveStorage(Storage storage);

    Storage deleteStorage(Long id, String businessName);
}
