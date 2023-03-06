package com.netgroup.web.inventory_system;

import com.netgroup.usecase.inventory_system.api.InventorySystemService;
import com.netgroup.usecase.inventory_system.api.ItemDto;
import com.netgroup.usecase.inventory_system.api.StorageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business")
public class InventorySystemController {
    private final InventorySystemService service;

    @GetMapping("/storage")
    public List<Object> getStorageContent(@RequestParam(name = "storageId", required = false) Optional<Long> possibleUpperStorageId, Authentication auth) {
        List<ItemDto> storageItems = service.getStorageItemsBy(possibleUpperStorageId, auth.getName());
        List<StorageDto> subStorages = service.getSubStoragesBy(possibleUpperStorageId, auth.getName());

        return Stream.concat(subStorages.stream(), storageItems.stream()).toList();
    }

    @PostMapping("/storage")
    public StorageDto addStorage(@RequestBody StorageDto storage, Authentication auth) {
        return service.saveStorage(storage, auth.getName());
    }

    @PostMapping("/item")
    public ItemDto addItem(@RequestBody ItemDto item, Authentication auth) {
        return service.saveItem(item, auth.getName());
    }
}
