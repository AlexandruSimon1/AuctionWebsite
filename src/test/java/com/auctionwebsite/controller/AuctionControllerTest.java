package com.auctionwebsite.controller;

import com.auctionwebsite.dto.AuctionDTO;
import com.auctionwebsite.service.impl.AuctionServiceImpl;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
@WebMvcTest(controllers = AuctionController.class)
public class AuctionControllerTest {
    private static final int AUCTION_DAYS = 7;
    private static final int ID_VALUE = 1;
    @Autowired
    private AuctionController auctionController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuctionServiceImpl auctionService;
    private AuctionDTO firstAuction;
    private AuctionDTO secondAuction;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    void setUp() {
        //setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(auctionController)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        startDate = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Bucharest"));
        endDate = LocalDateTime.ofInstant(Instant.now().plus(AUCTION_DAYS, ChronoUnit.DAYS), ZoneId.of("Europe/Bucharest"));

        firstAuction = new AuctionDTO();
        firstAuction.setId(ID_VALUE);
        firstAuction.setTitle("test");
        firstAuction.setDescription("Test");
        firstAuction.setStartDate(startDate);
        firstAuction.setEndDate(endDate);

        secondAuction = new AuctionDTO();
        secondAuction.setId(ID_VALUE);
        secondAuction.setTitle("test");
        secondAuction.setDescription("Test");
    }
    @Test
    void getAllAuctions() throws Exception {
        //given
        List<AuctionDTO> auctionDTOList = new ArrayList<>();
        auctionDTOList.add(firstAuction);
        auctionDTOList.add(secondAuction);
        //when
        when(auctionService.getAllAuctions()).thenReturn(auctionDTOList);
        //then
        mockMvc.perform(get("/api/v1/auctions")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    void getAuctionById() throws Exception {
        //when
        when(auctionService.getAuctionById(anyInt())).thenReturn(firstAuction);
        //then
        this.mockMvc.perform(get("/api/v1/auctions/{auctionId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void updateAuctionById() throws Exception {
        //when
        when(auctionService.updateAuctionById(any(), anyInt())).thenReturn(secondAuction);
        //then
        this.mockMvc.perform(put("/api/v1/auctions/{auctionId}", firstAuction.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(secondAuction)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id", Matchers.is(secondAuction.getId())));
    }

    @Test
    void deleteAuctionById() throws Exception {
        //when
        when(auctionService.deleteAuctionById(ID_VALUE)).thenReturn(firstAuction);
        //then
        this.mockMvc.perform(delete("/api/v1/auctions/{auctionId}", firstAuction.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(auctionService, times(1)).deleteAuctionById(ID_VALUE);
    }

//    @Test
//    void createAuction() throws Exception {
//        //when
//        when(auctionService.createAuction(Mockito.any(AuctionDTO.class))).thenReturn(firstAuction);
//        //then
//        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/auctions").
//                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
//                .content(this.mapper.writeValueAsBytes(firstAuction));
//        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(ID_VALUE))).
//                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(firstAuction)));
//    }
}
