package com.netgroup.repository.inventory_system;

import com.netgroup.entity.inventory_system.Storage;
import com.netgroup.repository.inventory_system.model.StorageModel;
import com.netgroup.usecase.inventory_system.api.StorageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class StorageRepositoryImpl implements StorageRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public List<Storage> getSubStoragesBy(Optional<Long> storageId, String businessName) {
        TypedQuery<StorageModel> query = manager.createQuery("select s from StorageModel s where (:storageId is null or upperStorageId = :storageId) and businessName = :businessName", StorageModel.class);
        query.setParameter("storageId", storageId.orElse(null));
        query.setParameter("businessName", businessName);

        return query.getResultStream().map(s -> Storage.builder()
                .id(s.getId())
                .name(s.getName())
                .businessName(s.getBusinessName())
                .upperStorageId(s.getUpperStorageId())
                .build()).toList();
    }

    @Override
    @Transactional
    public Storage saveStorage(Storage storage) {
        StorageModel storageModel = StorageModel.builder()
                .name(storage.getName())
                .businessName(storage.getBusinessName())
                .upperStorageId(storage.getUpperStorageId())
                .build();

        manager.persist(storageModel);
        storage.setId(storageModel.getId());

        return storage;
    }

    @Override
    @Transactional
    public Storage deleteStorage(Long id, String businessName) {
        TypedQuery<StorageModel> query = manager.createQuery("select s from StorageModel s where id = :id and businessName = :businessName", StorageModel.class);
        query.setParameter("id", id);
        query.setParameter("businessName", businessName);

        StorageModel storage = query.getResultStream().findFirst().orElseThrow(IllegalArgumentException::new);
        manager.remove(storage);

        return Storage.builder()
                .id(storage.getId())
                .name(storage.getName())
                .businessName(storage.getBusinessName())
                .upperStorageId(storage.getUpperStorageId())
                .build();
    }

}
