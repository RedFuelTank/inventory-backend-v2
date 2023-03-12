package com.netgroup.web.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netgroup.usecase.client.api.BusinessDto;
import com.netgroup.usecase.client.api.RepresentativeDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.netgroup.config.WebTestConfig.getObjectMapper;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ClientRequestBuilder {
    private MockMvc mockMvc;

    public ClientRequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions registerRepresentative(RepresentativeDto representativeDto) throws Exception {
        return mockMvc.perform(post("/user/registration").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToBytes(representativeDto)));
    }

    public static byte[] convertToBytes(Object object) throws JsonProcessingException {
        return getObjectMapper().writeValueAsBytes(object);
    }

    public ResultActions registerBusiness(BusinessDto businessDto) throws Exception {
        return mockMvc.perform(post("/business/registration").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToBytes(businessDto)));
    }
}
