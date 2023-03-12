package com.netgroup.web.inventory_system;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class InventorySystemRequestBuilder {
    private MockMvc mockMvc;

    public InventorySystemRequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    public ResultActions getStorageContent() throws Exception {
        return mockMvc.perform(get("/business/storage"));
    }
}
