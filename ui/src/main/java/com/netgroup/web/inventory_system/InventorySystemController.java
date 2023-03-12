package com.netgroup.web.inventory_system;

import com.netgroup.usecase.inventory_system.api.InventorySystemService;
import com.netgroup.usecase.inventory_system.api.ItemDto;
import com.netgroup.usecase.inventory_system.api.StorageDto;
import com.netgroup.usecase.payment.api.PaymentService;
import com.netgroup.usecase.statistics.api.StatisticsService;
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
    private final InventorySystemService inventoryService;
    private final PaymentService paymentService;
    private final StatisticsService statisticsService;

    @GetMapping("/storage")
    public List<Object> getStorageContent(@RequestParam(name = "storageId", required = false) Optional<Long> possibleUpperStorageId, Authentication auth) {
        List<ItemDto> storageItems = inventoryService.getStorageItemsBy(possibleUpperStorageId, auth.getName());
        List<StorageDto> subStorages = inventoryService.getSubStoragesBy(possibleUpperStorageId, auth.getName());

        return Stream.concat(subStorages.stream(), storageItems.stream()).toList();
    }

    @PostMapping("/storage")
    public StorageDto addStorage(@RequestBody StorageDto storage, Authentication auth) {
        return inventoryService.saveStorage(storage, auth.getName());
    }

    @PostMapping("/item")
    public ItemDto addItem(@RequestBody ItemDto item, Authentication auth) {
        ItemDto itemDto = inventoryService.saveItem(item, auth.getName());
        paymentService.incrementTotalAmount(auth.getName());
        statisticsService.addItemToStatistics(itemDto, auth.getName());

        return itemDto;
    }

    @DeleteMapping("/item/{id}")
    public ItemDto deleteItem(@PathVariable Long id, Authentication auth) {
        statisticsService.setItemOutdated(id, auth.getName());
        ItemDto itemDto = inventoryService.deleteItem(id, auth.getName());
        paymentService.decrementTotalAmount(auth.getName());

        return itemDto;
    }

    @DeleteMapping("/storage/{id}")
    public StorageDto deleteStorage(@PathVariable Long id, Authentication auth) {
        return inventoryService.deleteStorage(id, auth.getName());
    }
}
