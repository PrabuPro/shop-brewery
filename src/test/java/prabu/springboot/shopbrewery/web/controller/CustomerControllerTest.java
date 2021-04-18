package prabu.springboot.shopbrewery.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import prabu.springboot.shopbrewery.web.model.CustomerDTO;
import prabu.springboot.shopbrewery.web.service.CustomerService;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customerDTO = CustomerDTO.builder().id(UUID.randomUUID()).name("name").build();
    }

    @Test
    void getCustomer() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(customerDTO);

        mockMvc.perform(get(CustomerController.API_V_1_CUSTOMER + "/" + customerDTO.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(customerDTO.getId().toString())));
    }

    @Test
    void saveCustomer() throws Exception {
        CustomerDTO saveCustomer = customerDTO;
        saveCustomer.setId(null);
        CustomerDTO savedCustomer = CustomerDTO.builder().id(UUID.randomUUID()).name("name").build();

        given(customerService.saveCustomer(any(CustomerDTO.class))).willReturn(savedCustomer);

        mockMvc.perform(post(CustomerController.API_V_1_CUSTOMER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveCustomer)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCustomer() throws Exception {
        mockMvc.perform(put(CustomerController.API_V_1_CUSTOMER + "/" + customerDTO.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNoContent());

        then(customerService).should().updateCustomer(any(), any());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.API_V_1_CUSTOMER + "/" + customerDTO.getId().toString()))
                .andExpect(status().isNoContent());

        then(customerService).should().deleteCustomerById(any());
    }

}