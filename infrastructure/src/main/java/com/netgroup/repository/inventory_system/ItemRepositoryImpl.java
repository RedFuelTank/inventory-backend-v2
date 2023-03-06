package com.netgroup.repository.inventory_system;

import com.netgroup.entity.inventory_system.Item;
import com.netgroup.repository.inventory_system.model.ItemModel;
import com.netgroup.usecase.inventory_system.api.ItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public List<Item> getStorageItemsBy(Optional<Long> possibleStorageId, String businessName) {
        TypedQuery<ItemModel> query = manager.createQuery("select i from ItemModel i where storageId = :storageId and businessName = :businessName", ItemModel.class);
        query.setParameter("storageId", possibleStorageId.orElse(null));
        query.setParameter("businessName", businessName);

        return query.getResultStream().map(s -> Item.builder()
                .id(s.getId())
                .name(s.getName())
                .businessName(s.getBusinessName())
                .storageId(s.getStorageId())
                .build()).toList();
    }
}
