package com.netgroup.web.inventory_system;

import com.netgroup.usecase.inventory_system.api.InventorySystemService;
import com.netgroup.usecase.inventory_system.api.ItemDto;
import com.netgroup.usecase.inventory_system.api.StorageDto;
import com.netgroup.usecase.payment.api.PaymentService;
import com.netgroup.usecase.statistics.api.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Object> getStorageContent(@RequestParam(name = "storageId", required = false) Optional<Long> possibleUpperStorageId, Authentication auth, Pageable pageable) {
        double storageSize = Math.ceil(pageable.getPageSize() / 2d);
        double itemSize = Math.floor(pageable.getPageSize() / 2d);

        Page<ItemDto> storageItems = inventoryService.getStorageItemsBy(
                possibleUpperStorageId,
                auth.getName(),
                PageRequest.of(pageable.getPageNumber(), (int) itemSize)
        );
        Page<StorageDto> subStorages = inventoryService.getSubStoragesBy(
                possibleUpperStorageId,
                auth.getName(),
                PageRequest.of(pageable.getPageNumber(), (int) storageSize));

        List<Object> objects = Stream.concat(
                subStorages.getContent().stream(),
                storageItems.getContent().stream()
        ).toList();

        return new PageImpl<>(objects, pageable, storageItems.getTotalElements() + subStorages.getTotalElements());
    }

    @GetMapping("/search")
    public Page<Object> getContentBy(@RequestParam String name, Authentication auth, Pageable pageable) {
        double storageSize = Math.ceil(pageable.getPageSize() / 2d);
        double itemSize = Math.floor(pageable.getPageSize() / 2d);

        Page<ItemDto> storageItems = inventoryService.findItemsBy(
                name,
                auth.getName(),
                PageRequest.of(pageable.getPageNumber(), (int) itemSize)
        );
        Page<StorageDto> subStorages = inventoryService.findStoragesBy(
                name,
                auth.getName(),
                PageRequest.of(pageable.getPageNumber(), (int) storageSize)
        );

        List<Object> objects = Stream.concat(
                subStorages.getContent().stream(),
                storageItems.getContent().stream()
        ).toList();

        return new PageImpl<>(objects, pageable, storageItems.getTotalElements() + subStorages.getTotalElements());
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
