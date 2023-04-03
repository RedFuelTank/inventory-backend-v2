package com.netgroup.repository.inventory_system;

import com.netgroup.entity.inventory_system.Item;
import com.netgroup.repository.inventory_system.model.ItemModel;
import com.netgroup.usecase.inventory_system.api.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryImpl extends ItemRepository,
        PagingAndSortingRepository<ItemModel, Long>,
        JpaRepository<ItemModel, Long> {

    Page<ItemModel> findAllByStorageIdAndBusinessName(Long storageId,
                                                      String businessName,
                                                      Pageable pageable);

    Page<ItemModel> findAllByNameLikeAndBusinessName(String name,
                                                     String businessName,
                                                     Pageable pageable);

    ItemModel findItemModelByIdAndBusinessName(Long id, String businessName);

    @Override
    default Page<Item> getStorageItemsBy(Long storageId, String businessName, Pageable pageable) {
        Page<ItemModel> itemModelsPage = findAllByStorageIdAndBusinessName(storageId, businessName, pageable);

        List<ItemModel> itemModels = itemModelsPage.getContent();
        List<Item> items = itemModels.stream().map(i -> Item.builder()
                .id(i.getId())
                .name(i.getName())
                .businessName(i.getBusinessName())
                .storageId(i.getStorageId())
                .build()).toList();

        return new PageImpl<>(items, pageable, itemModelsPage.getTotalElements());
    }

    ;

    @Override
    default Item saveItem(Item item) {
        ItemModel savedItem = save(ItemModel.builder()
                .name(item.getName())
                .businessName(item.getBusinessName())
                .storageId(item.getStorageId())
                .build());

        item.setId(savedItem.getId());

        return item;
    }

    @Override
    default Item deleteItem(Long id, String businessName) {
        ItemModel itemModel = findItemModelByIdAndBusinessName(id, businessName);

        if (itemModel == null) {
            throw new IllegalArgumentException();
        }

        delete(itemModel);

        return Item.builder()
                .id(itemModel.getId())
                .name(itemModel.getName())
                .businessName(itemModel.getBusinessName())
                .storageId(itemModel.getStorageId())
                .build();
    }

    @Override
    default Page<Item> findItemsBy(String name, String businessName, Pageable pageable) {
        Page<ItemModel> itemModelsPage = findAllByNameLikeAndBusinessName(name, businessName, pageable);

        List<ItemModel> itemModels = itemModelsPage.getContent();
        List<Item> items = itemModels.stream().map(i -> Item.builder()
                .id(i.getId())
                .name(i.getName())
                .storageId(i.getStorageId())
                .businessName(i.getBusinessName())
                .build()).toList();

        return new PageImpl<>(items, pageable, itemModelsPage.getTotalElements());
    }

    @Override
    default Item getItemById(Long id) {
        Optional<ItemModel> possibleItem = findById(id);

        if (possibleItem.isPresent()) {
            ItemModel itemModel = possibleItem.get();
            return Item.builder()
                    .id(itemModel.getId())
                    .name(itemModel.getName())
                    .storageId(itemModel.getStorageId())
                    .businessName(itemModel.getBusinessName())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    };
}
