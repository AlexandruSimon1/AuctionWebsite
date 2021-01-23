package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.model.Bidding;
import com.auctionwebsite.repository.BiddingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BiddingServiceImplTest {
    private static final int ID_VALUE = 1;
    private Bidding firstBidding;
    private Bidding secondBidding;
    @InjectMocks
    private BiddingServiceImpl biddingService;
    @Mock
    private BiddingRepository biddingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        biddingService = new BiddingServiceImpl(biddingRepository);

        firstBidding = new Bidding();
        firstBidding.setId(ID_VALUE);

        secondBidding = new Bidding();
        secondBidding.setId(ID_VALUE);
    }

    @Test
    void getAllBiddings() {
        //given
        List<Bidding> getAllBiddings = new ArrayList<>();
        getAllBiddings.add(firstBidding);
        getAllBiddings.add(secondBidding);
        //when
        when(biddingRepository.findAll()).thenReturn(getAllBiddings);
        List<BiddingDTO> biddingDTOList = biddingService.getAllBiddings();
        //then
        assertEquals(getAllBiddings.size(), biddingDTOList.size());
        verify(biddingRepository, times(1)).findAll();
    }

    @Test
    void getBiddingById() {
        //when
        when(biddingRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstBidding));
        BiddingDTO biddingDTO = biddingService.getBiddingById(ID_VALUE);
        //then
        assertEquals(firstBidding.getId(), biddingDTO.getId());
        verify(biddingRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteBiddingById() {
        //when
        when(biddingRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstBidding));
        biddingRepository.deleteById(ID_VALUE);
        //then
        verify(biddingRepository, times(1)).deleteById(ID_VALUE);
        BiddingDTO biddingDTO = biddingService.deleteBiddingById(ID_VALUE);
        assertEquals(firstBidding.getId(), biddingDTO.getId());
    }

    @Test
    void updateBiddingById() {
        //given
        BiddingDTO dto = new BiddingDTO();
        dto.setId(ID_VALUE);
        //when
        when(biddingRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstBidding));
        when(biddingRepository.save(firstBidding)).thenReturn(firstBidding);
        BiddingDTO updateBidding = biddingService.updateBiddingById(dto, ID_VALUE);
        //then
        assertNotNull(updateBidding);
        assertEquals(firstBidding.getId(), updateBidding.getId());
        verify(biddingRepository, times(1)).save(firstBidding);
    }

    @Test
    void createBidding() {
        //given
        BiddingDTO dto = new BiddingDTO();
        dto.setId(ID_VALUE);
        //when
        when(biddingRepository.save(firstBidding)).thenReturn(firstBidding);
        BiddingDTO biddingDTO = biddingService.createBidding(dto);
        //then
        assertNotNull(firstBidding);
        assertEquals(firstBidding.getId(), biddingDTO.getId());
    }
}
