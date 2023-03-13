package com.netgroup.web.inventory_system;

import com.netgroup.usecase.image.api.ImageService;
import com.netgroup.usecase.inventory_system.api.InventorySystemService;
import com.netgroup.usecase.inventory_system.api.ItemDto;
import com.netgroup.usecase.inventory_system.api.StorageDto;
import com.netgroup.usecase.payment.api.PaymentService;
import com.netgroup.usecase.statistics.api.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventorySystemController.class)
@WithMockUser(username = "user", password = "user", authorities = "REPRESENTATIVE")
public class InventorySystemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    InventorySystemService inventoryService;

    @MockBean
    PaymentService paymentService;

    @MockBean
    StatisticsService statisticsService;

    @MockBean
    ImageService imageService;

    InventorySystemRequestBuilder requestBuilder;

    @BeforeEach
    void init() {
        requestBuilder = new InventorySystemRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("Get storage content")
    class GetStorageContent {
        private final static List<StorageDto> STORAGE_PAGE_CONTENT;
        private final static List<ItemDto> ITEM_PAGE_CONTENT;
        private final static int STORAGE_TOTAL_ELEMENTS = 20;
        private final static int ITEM_TOTAL_ELEMENTS = 10;
        private final static int TOTAL_ELEMENTS = STORAGE_TOTAL_ELEMENTS + ITEM_TOTAL_ELEMENTS;
        public static final int PAGE_SIZE = 2;


        static {
            STORAGE_PAGE_CONTENT = List.of(
                    new StorageDto(1L, "First storage", null),
                    new StorageDto(2L, "Second storage", null)
            );
            ITEM_PAGE_CONTENT = List.of(
                    new ItemDto(1L, "First item", null),
                    new ItemDto(2L, "Second item", null)
            );
        }

        @BeforeEach
        void init() {
            given(inventoryService.getStorageItemsBy(any(), any(), any())).willReturn(new PageImpl<>(
                    ITEM_PAGE_CONTENT, Pageable.ofSize(PAGE_SIZE), ITEM_TOTAL_ELEMENTS)
            );
            given(inventoryService.getSubStoragesBy(any(), any(), any())).willReturn(new PageImpl<>(
                    STORAGE_PAGE_CONTENT, Pageable.ofSize(PAGE_SIZE), STORAGE_TOTAL_ELEMENTS)
            );
        }

        @Test
        @DisplayName("Should return the HTTP status 200")
        void shouldReturnHttpStatusOk() throws Exception {
            requestBuilder.getStorageContent()
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return HTTP response with JSON media-type")
        void shouldReturnCorrectMethodType() throws Exception {
            requestBuilder.getStorageContent()
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return correct number of elements")
        void shouldReturnCorrectNumberElements() throws Exception {
            requestBuilder.getStorageContent()
                    .andExpect(jsonPath("$.content", hasSize(4)));
        }

        @Test
        @DisplayName("Should return correct number of total elements")
        void shouldReturnCorrectTotalElements() throws Exception {
            requestBuilder.getStorageContent()
                    .andExpect(jsonPath("$.totalElements", equalTo(TOTAL_ELEMENTS)));
        }
    }
}
