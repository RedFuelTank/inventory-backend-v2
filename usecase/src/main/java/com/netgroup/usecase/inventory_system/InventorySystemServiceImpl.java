package com.netgroup.usecase.inventory_system;

import com.netgroup.entity.inventory_system.Item;
import com.netgroup.entity.inventory_system.Storage;
import com.netgroup.usecase.inventory_system.api.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class InventorySystemServiceImpl implements InventorySystemService {
    private final StorageRepository storageRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemDto> getStorageItemsBy(Optional<Long> storageId, String businessName) {
        List<Item> storageItems = itemRepository.getStorageItemsBy(storageId, businessName);
        return storageItems.stream().map(i -> ItemDto.builder()
                .id(i.getId())
                .storageId(i.getStorageId())
                .name(i.getName())
                .build()).toList();
    }

    @Override
    public List<StorageDto> getSubStoragesBy(Optional<Long> storageId, String businessName) {
        List<Storage> subStorages = storageRepository.getSubStoragesBy(storageId, businessName);
        return subStorages.stream().map(s -> StorageDto.builder()
                .id(s.getId())
                .upperStorageId(s.getUpperStorageId())
                .name(s.getName())
                .build()).toList();
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
}
