package com.auctionwebsite.controller;

import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.service.impl.BiddingServiceImpl;
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
@WebMvcTest(controllers = BiddingController.class)
public class BiddingControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private BiddingController biddingController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BiddingServiceImpl biddingService;
    private BiddingDTO firstBidding;
    private BiddingDTO secondBidding;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(biddingController)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        firstBidding = new BiddingDTO();
        firstBidding.setId(ID_VALUE);

        secondBidding = new BiddingDTO();
        secondBidding.setId(ID_VALUE);
    }

    @Test
    void getAllBiddings() throws Exception {
        //given
        List<BiddingDTO> biddingDTOList = new ArrayList<>();
        biddingDTOList.add(firstBidding);
        biddingDTOList.add(secondBidding);
        //when
        when(biddingService.getAllBiddings()).thenReturn(biddingDTOList);
        //then
        mockMvc.perform(get("/api/v1/biddings")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getBiddingById() throws Exception {
        //when
        when(biddingService.getBiddingById(anyInt())).thenReturn(firstBidding);
        //then
        this.mockMvc.perform(get("/api/v1/biddings/{biddingId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void updateBiddingById() throws Exception {
        //when
        when(biddingService.updateBiddingById(any(), anyInt())).thenReturn(secondBidding);
        //then
        this.mockMvc.perform(put("/api/v1/biddings/{biddingId}", firstBidding.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(secondBidding)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id", Matchers.is(secondBidding.getId())));
    }

    @Test
    void deleteBiddingById() throws Exception {
        //when
        when(biddingService.deleteBiddingById(ID_VALUE)).thenReturn(firstBidding);
        //then
        this.mockMvc.perform(delete("/api/v1/biddings/{biddingId}", firstBidding.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(biddingService, times(1)).deleteBiddingById(ID_VALUE);
    }

    @Test
    void createBidding() throws Exception {
        //when
        when(biddingService.createBidding(Mockito.any(BiddingDTO.class))).thenReturn(firstBidding);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/biddings").
                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(firstBidding));
        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(ID_VALUE))).
                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(firstBidding)));
    }
}
