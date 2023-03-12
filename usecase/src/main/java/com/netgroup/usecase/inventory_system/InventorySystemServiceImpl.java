package com.netgroup.usecase.inventory_system;

import com.netgroup.entity.inventory_system.Item;
import com.netgroup.entity.inventory_system.Storage;
import com.netgroup.usecase.inventory_system.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class InventorySystemServiceImpl implements InventorySystemService {
    private final StorageRepository storageRepository;
    private final ItemRepository itemRepository;

    @Override
    public Page<ItemDto> getStorageItemsBy(Optional<Long> storageId, String businessName, Pageable pageable) {
        Page<Item> storageItemsPage = itemRepository.getStorageItemsBy(
                storageId.orElse(null),
                businessName,
                pageable);

        List<Item> storageItems = storageItemsPage.getContent();

        List<ItemDto> storageItemDtos = storageItems.stream().map(i -> ItemDto.builder()
                .id(i.getId())
                .name(i.getName())
                .storageId(i.getStorageId())
                .build()).toList();

        return new PageImpl<>(storageItemDtos, pageable, storageItemsPage.getTotalElements());
    }

    @Override
    public Page<StorageDto> getSubStoragesBy(Optional<Long> storageId, String businessName, Pageable pageable) {
        Page<Storage> subStoragesPage = storageRepository.getSubStoragesBy(
                storageId.orElse(null),
                businessName,
                pageable
        );

        List<Storage> subStorages = subStoragesPage.getContent();

        List<StorageDto> subStoragesDtos = subStorages.stream().map(s -> StorageDto.builder()
                .id(s.getId())
                .name(s.getName())
                .upperStorageId(s.getUpperStorageId())
                .build()).toList();

        return new PageImpl<>(subStoragesDtos, pageable, subStoragesPage.getTotalElements());
    }

    @Override
    public StorageDto saveStorage(StorageDto storageDto, String businessName) {
        Storage storage = Storage.builder()
                .upperStorageId(storageDto.getUpperStorageId())
                .name(storageDto.getName())
                .businessName(businessName)
                .build();

        storageDto.setId(storageRepository.saveStorage(storage).getId());
        return storageDto;
    }

    @Override
    public ItemDto saveItem(ItemDto itemDto, String businessName) {
        Item item = Item.builder()
                .name(itemDto.getName())
                .businessName(businessName)
                .storageId(itemDto.getStorageId())
                .build();

        itemDto.setId(itemRepository.saveItem(item).getId());
        return itemDto;
    }

    @Override
    public ItemDto deleteItem(Long id, String businessName) {
        Item item = itemRepository.deleteItem(id, businessName);
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .storageId(item.getStorageId())
                .build();
    }

    @Override
    public StorageDto deleteStorage(Long id, String businessName) {
        Storage storage = storageRepository.deleteStorage(id, businessName);
        return StorageDto.builder()
                .id(storage.getId())
                .name(storage.getName())
                .upperStorageId(storage.getUpperStorageId())
                .build();
    }

    @Override
    public Page<ItemDto> findItemsBy(String name, String businessName, Pageable pageable) {
        Page<Item> itemsPage = itemRepository.findItemsBy(name, businessName, pageable);

        List<Item> items = itemsPage.getContent();
        List<ItemDto> itemDtos = items.stream().map(i -> ItemDto.builder()
                .id(i.getId())
                .name(i.getName())
                .storageId(i.getStorageId())
                .build()).toList();

        return new PageImpl<>(itemDtos, pageable, itemsPage.getTotalElements());
    }

    @Override
    public Page<StorageDto> findStoragesBy(String name, String businessName, Pageable pageable) {
        Page<Storage> storagesPage = storageRepository.findStoragesBy(name, businessName, pageable);

        List<Storage> storages = storagesPage.getContent();
        List<StorageDto> storageDtos = storages.stream().map(s -> StorageDto.builder()
                .id(s.getId())
                .name(s.getName())
                .upperStorageId(s.getUpperStorageId())
                .build()).toList();

        return new PageImpl<>(storageDtos, pageable, storagesPage.getTotalElements());
    }
}
