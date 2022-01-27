package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.AuctionDTO;
import com.auctionwebsite.model.Auction;
import com.auctionwebsite.repository.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceImplTest {
    private static final int AUCTION_DAYS = 7;
    private static final int ID_VALUE = 1;
    private Auction firstAuction;
    private Auction secondAuction;
    private Date startDate;
    private Date endDate;
    @InjectMocks
    private AuctionServiceImpl auctionService;
    @Mock
    private AuctionRepository auctionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        auctionService = new AuctionServiceImpl(auctionRepository);
        startDate = Date.from(Instant.now());
        endDate = Date.from(Instant.now().plus(AUCTION_DAYS, ChronoUnit.DAYS));

        firstAuction = new Auction();
        firstAuction.setId(ID_VALUE);
        firstAuction.setTitle("test");
        firstAuction.setDescription("Test");
        firstAuction.setStartDate(startDate);
        firstAuction.setEndDate(endDate);

        secondAuction = new Auction();
        secondAuction.setId(ID_VALUE);
        secondAuction.setTitle("test");
        secondAuction.setDescription("Test");
    }

    @Test
    void getAllAuctions() {
        //given
        List<Auction> getAllAuctions = new ArrayList<>();
        getAllAuctions.add(firstAuction);
        getAllAuctions.add(secondAuction);
        //when
        when(auctionRepository.findAll()).thenReturn(getAllAuctions);
        List<AuctionDTO> auctionDTOList = auctionService.getAllAuctions();
        //then
        assertEquals(getAllAuctions.size(), auctionDTOList.size());
        verify(auctionRepository, times(1)).findAll();
    }

    @Test
    void getAuctionById() {
        //when
        when(auctionRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstAuction));
        AuctionDTO auctionDTO = auctionService.getAuctionById(ID_VALUE);
        //then
        assertEquals(firstAuction.getId(), auctionDTO.getId());
        assertEquals(firstAuction.getTitle(), auctionDTO.getTitle());
        assertEquals(firstAuction.getDescription(), auctionDTO.getDescription());
        verify(auctionRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteAuctionById() {
        //when
        when(auctionRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstAuction));
        auctionRepository.deleteById(ID_VALUE);
        //then
        verify(auctionRepository, times(1)).deleteById(ID_VALUE);
        AuctionDTO auctionDTO = auctionService.deleteAuctionById(ID_VALUE);
        assertEquals(firstAuction.getId(), auctionDTO.getId());
        assertEquals(firstAuction.getTitle(), auctionDTO.getTitle());
        assertEquals(firstAuction.getDescription(), auctionDTO.getDescription());
    }

    @Test
    void updateAuctionById() {
        //given
        AuctionDTO dto = new AuctionDTO();
        dto.setId(ID_VALUE);
        dto.setTitle("test");
        dto.setDescription("Test");
        //when
        when(auctionRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstAuction));
        when(auctionRepository.save(firstAuction)).thenReturn(firstAuction);
        AuctionDTO updateAuction = auctionService.updateAuctionById(dto, ID_VALUE);
        //then
        assertNotNull(updateAuction);
        assertEquals(firstAuction.getId(), updateAuction.getId());
        assertEquals(firstAuction.getTitle(), updateAuction.getTitle());
        assertEquals(firstAuction.getDescription(), updateAuction.getDescription());
        verify(auctionRepository, times(1)).save(firstAuction);
    }

//    @Test
//    void createAuction() {
//        //given
//        AuctionDTO dto = new AuctionDTO();
//        dto.setId(ID_VALUE);
//        dto.setTitle("test");
//        dto.setDescription("Test");
//        dto.setStartDate(startDate);
//        dto.setEndDate(endDate);
//        //when
//        when(auctionRepository.save(firstAuction)).thenReturn(firstAuction);
//        AuctionDTO auctionDTO = auctionService.createAuction(dto);
//        //then
//        assertNotNull(firstAuction);
//        assertEquals(firstAuction.getId(), auctionDTO.getId());
//        assertEquals(firstAuction.getTitle(), auctionDTO.getTitle());
//        assertEquals(firstAuction.getDescription(), auctionDTO.getDescription());
//    }
}
