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

@Repository
public class StorageRepositoryImpl implements StorageRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public List<Storage> getSubStoragesBy(int storageId, String businessName) {
        TypedQuery<StorageModel> query = manager.createQuery("select s from StorageModel s where upperStorageId = :storageId and name = :name", StorageModel.class);
        query.setParameter("upperStorageId", storageId);
        query.setParameter("name", businessName);

        return query.getResultStream().map(s -> Storage.builder()
                .id(s.getId())
                .name(s.getName())
                .upperStorageId(s.getUpperStorageId())
                .build()).toList();
    }

}
