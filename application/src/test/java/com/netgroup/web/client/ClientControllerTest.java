package com.netgroup.web.client;

import com.netgroup.usecase.client.api.BusinessDto;
import com.netgroup.usecase.client.api.ClientService;
import com.netgroup.usecase.client.api.RepresentativeDto;
import com.netgroup.usecase.payment.api.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
@WithMockUser(username = "user", password = "user", authorities = "REPRESENTATIVE")
class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private PaymentService paymentService;
    private ClientRequestBuilder requestBuilder;

    @BeforeEach
    void init() {
        requestBuilder = new ClientRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("Register a new representative")
    class RegisterRepresentative {
        private static final String REPRESENTATIVE_NAME = "user";
        private static final String REPRESENTATIVE_PASSWORD = "user";

        private RepresentativeDto representativeDto;

        @BeforeEach
        void init() {
            representativeDto = new RepresentativeDto(REPRESENTATIVE_NAME, REPRESENTATIVE_PASSWORD);
            given(clientService.registerUser(any())).willReturn(representativeDto);
        }

        @Test
        @DisplayName("Should return HTTP response code 201")
        void shouldReturnHttpResponseCodeCreated() throws Exception {
            requestBuilder.registerRepresentative(representativeDto).andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Should return HTTP response with JSON media-type")
        void shouldReturnHttpResponseJsonMediaType() throws Exception {
            requestBuilder.registerRepresentative(representativeDto)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return correct HTTP response body")
        void shouldReturnCorrectHttpResponseBody() throws Exception {
            requestBuilder.registerRepresentative(representativeDto)
                    .andExpect(jsonPath("$.username", equalTo(REPRESENTATIVE_NAME)))
                    .andExpect(jsonPath("$.password", equalTo(REPRESENTATIVE_PASSWORD)));
        }
    }

    @Nested
    @DisplayName("Register a new business")
    class RegisterBusiness {
        private static final String BUSINESS_NAME = "business";
        private static final String BUSINESS_PASSWORD = "business";
        private BusinessDto businessDto;

        @BeforeEach
        void init() {
            businessDto = new BusinessDto(BUSINESS_NAME, BUSINESS_PASSWORD);
            given(clientService.registerBusiness(any(), any())).willReturn(businessDto);
        }

        @Test
        @DisplayName("Should return HTTP response code 201")
        void shouldReturnHttpResponseCodeCreated() throws Exception {
            requestBuilder.registerBusiness(businessDto)
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Should return HTTP response with JSON media-type")
        void shouldReturnHttpResponseJsonMediaType() throws Exception {
            requestBuilder.registerBusiness(businessDto)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return correct HTTP response body")
        void shouldReturnCorrectHttpResponseBody() throws Exception {
            requestBuilder.registerBusiness(businessDto)
                    .andExpect(jsonPath("$.name", equalTo(BUSINESS_NAME)))
                    .andExpect(jsonPath("$.password", equalTo(BUSINESS_PASSWORD)));
        }
    }
}
