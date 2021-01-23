package com.auctionwebsite.controller;

import com.auctionwebsite.dto.PurchasingDTO;
import com.auctionwebsite.service.impl.PurchasingServiceImpl;
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
@WebMvcTest(controllers = PurchasingController.class)
public class PurchasingControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private PurchasingController purchasingController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PurchasingServiceImpl purchasingService;
    private PurchasingDTO firstPurchasing;
    private PurchasingDTO secondPurchasing;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(purchasingController)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        firstPurchasing = new PurchasingDTO();
        firstPurchasing.setId(ID_VALUE);

        secondPurchasing = new PurchasingDTO();
        secondPurchasing.setId(ID_VALUE);
    }

    @Test
    void getAllPurchases() throws Exception {
        //given
        List<PurchasingDTO> purchasingDTOList = new ArrayList<>();
        purchasingDTOList.add(firstPurchasing);
        purchasingDTOList.add(secondPurchasing);
        //when
        when(purchasingService.getAllPurchases()).thenReturn(purchasingDTOList);
        //then
        mockMvc.perform(get("/api/v1/purchases")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getPurchasingById() throws Exception {
        //when
        when(purchasingService.getPurchasingById(anyInt())).thenReturn(firstPurchasing);
        //then
        this.mockMvc.perform(get("/api/v1/purchases/{purchasingId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void updatePurchasingById() throws Exception {
        //when
        when(purchasingService.updatePurchasingById(any(), anyInt())).thenReturn(secondPurchasing);
        //then
        this.mockMvc.perform(put("/api/v1/purchases/{purchasingId}", firstPurchasing.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(secondPurchasing)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id", Matchers.is(secondPurchasing.getId())));
    }

    @Test
    void deletePurchasingById() throws Exception {
        //when
        when(purchasingService.deletePurchasingById(ID_VALUE)).thenReturn(firstPurchasing);
        //then
        this.mockMvc.perform(delete("/api/v1/purchases/{purchasingId}", firstPurchasing.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(purchasingService, times(1)).deletePurchasingById(ID_VALUE);
    }

    @Test
    void createPurchasing() throws Exception {
        //when
        when(purchasingService.createPurchasing(Mockito.any(PurchasingDTO.class))).thenReturn(firstPurchasing);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/purchases").
                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(firstPurchasing));
        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(ID_VALUE))).
                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(firstPurchasing)));
    }
}
