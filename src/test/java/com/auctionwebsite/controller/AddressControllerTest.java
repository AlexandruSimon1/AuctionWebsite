package com.auctionwebsite.controller;

import com.auctionwebsite.dto.AddressDTO;
import com.auctionwebsite.service.impl.AddressServiceImpl;
import com.auctionwebsite.utils.ExceptionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//This type of testing is used in case when we don't have any kind of security in our REST API
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private AddressController addressController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddressServiceImpl addressService;
    private AddressDTO firstAddress;
    private AddressDTO secondAddress;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        firstAddress = new AddressDTO();
        firstAddress.setId(ID_VALUE);
        firstAddress.setCity("Douala");
        firstAddress.setProvince("Cameron");
        firstAddress.setAddress("United");

        secondAddress = new AddressDTO();
        secondAddress.setId(ID_VALUE);
        secondAddress.setCity("Douala");
        secondAddress.setProvince("Cameron");
        secondAddress.setAddress("United");
    }

    @Test
    void getAllAddresses() throws Exception {
        //given
        List<AddressDTO> addressDTOList = new ArrayList<>();
        addressDTOList.add(firstAddress);
        addressDTOList.add(secondAddress);
        //when
        when(addressService.getAllAddresses()).thenReturn(addressDTOList);
        //then
        mockMvc.perform(get("/api/v1/addresses")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getAddressById() throws Exception {
        //when
        when(addressService.getAddressById(anyInt())).thenReturn(firstAddress);
        //then
        this.mockMvc.perform(get("/api/v1/addresses/{addressId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void updateAddressById() throws Exception {
        //when
        when(addressService.updateAddressById(any(), anyInt())).thenReturn(secondAddress);
        //then
        this.mockMvc.perform(put("/api/v1/addresses/{addressId}", firstAddress.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(secondAddress)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("city", Matchers.is(secondAddress.getCity())));
    }

    @Test
    void deleteAddressById() throws Exception {
        //when
        when(addressService.deleteAddressById(ID_VALUE)).thenReturn(firstAddress);
        //then
        this.mockMvc.perform(delete("/api/v1/addresses/{addressId}", firstAddress.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(addressService, times(1)).deleteAddressById(ID_VALUE);
    }

    @Test
    void createAddress() throws Exception {
        //when
        when(addressService.createAddress(Mockito.any(AddressDTO.class))).thenReturn(firstAddress);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/addresses").
                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(firstAddress));
        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.city", is("Douala"))).
                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(firstAddress)));
    }
}
