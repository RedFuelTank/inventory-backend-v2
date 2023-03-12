package com.netgroup.repository.inventory_system;

import com.netgroup.entity.inventory_system.Storage;
import com.netgroup.repository.inventory_system.model.StorageModel;
import com.netgroup.usecase.inventory_system.api.StorageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StorageRepositoryImpl extends StorageRepository,
        PagingAndSortingRepository<StorageModel, Long>,
        JpaRepository<StorageModel, Long> {

    StorageModel findStorageModelByIdAndBusinessName(Long id, String businessName);

    Page<StorageModel> findAllByUpperStorageIdAndBusinessName(Long upperStorageId, String businessName, Pageable pageable);

    Page<StorageModel> findAllByNameLikeAndBusinessName(String name, String businessName, Pageable pageable);

    @Override
    default Page<Storage> getSubStoragesBy(Long storageId, String businessName, Pageable pageable) {
        Page<StorageModel> storageModelsPage = findAllByUpperStorageIdAndBusinessName(storageId, businessName, pageable);

        List<StorageModel> storageModels = storageModelsPage.getContent();
        List<Storage> storages = storageModels.stream().map(s -> Storage.builder()
                .id(s.getId())
                .name(s.getName())
                .businessName(s.getBusinessName())
                .upperStorageId(s.getUpperStorageId())
                .build()).toList();

        return new PageImpl<>(storages, pageable, storageModelsPage.getTotalElements());
    };

    @Override
    default Storage saveStorage(Storage storage) {
        StorageModel saved = save(StorageModel.builder()
                .name(storage.getName())
                .businessName(storage.getBusinessName())
                .upperStorageId(storage.getUpperStorageId())
                .build());

        storage.setId(saved.getId());

        return storage;
    }

    @Override
    default Storage deleteStorage(@Param("id") Long id,
                          @Param("businessName") String businessName) {
        StorageModel storageModel = findStorageModelByIdAndBusinessName(id, businessName);

        if (storageModel == null) {
            throw new IllegalArgumentException();
        }

        delete(storageModel);

        return Storage.builder()
                .id(storageModel.getId())
                .name(storageModel.getName())
                .businessName(storageModel.getBusinessName())
                .upperStorageId(storageModel.getUpperStorageId())
                .build();
    }

    @Override
    default Page<Storage> findStoragesBy(String name, String businessName, Pageable pageable) {
        Page<StorageModel> storageModelsPage = findAllByNameLikeAndBusinessName(name, businessName, pageable);

        List<StorageModel> storageModels = storageModelsPage.getContent();
        List<Storage> storages = storageModels.stream().map(s -> Storage.builder()
                .id(s.getId())
                .name(s.getName())
                .businessName(s.getBusinessName())
                .upperStorageId(s.getUpperStorageId())
                .build()).toList();

        return new PageImpl<>(storages, pageable, storageModelsPage.getTotalElements());
    };
}
