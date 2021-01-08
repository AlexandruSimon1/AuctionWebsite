package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.PurchasingDTO;
import com.auctionwebsite.model.Purchasing;
import com.auctionwebsite.repository.PurchasingRepository;
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
public class PurchasingServiceImplTest {
    private static final int ID_VALUE = 1;
    private Purchasing firstPurchasing;
    private Purchasing secondPurchasing;
    @InjectMocks
    private PurchasingServiceImpl purchasingService;
    @Mock
    private PurchasingRepository purchasingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        purchasingService = new PurchasingServiceImpl(purchasingRepository);

        firstPurchasing = new Purchasing();
        firstPurchasing.setId(ID_VALUE);

        secondPurchasing = new Purchasing();
        secondPurchasing.setId(ID_VALUE);
    }

    @Test
    void getAllPurchases() {
        //given
        List<Purchasing> getAllPurchases = new ArrayList<>();
        getAllPurchases.add(firstPurchasing);
        getAllPurchases.add(firstPurchasing);
        //when
        when(purchasingRepository.findAll()).thenReturn(getAllPurchases);
        List<PurchasingDTO> purchasingDTOList = purchasingService.getAllPurchases();
        //then
        assertEquals(getAllPurchases.size(), purchasingDTOList.size());
        verify(purchasingRepository, times(1)).findAll();
    }

    @Test
    void getPurchasingById() {
        //when
        when(purchasingRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstPurchasing));
        PurchasingDTO purchasingDTO = purchasingService.getPurchasingById(ID_VALUE);
        //then
        assertEquals(firstPurchasing.getId(), purchasingDTO.getId());
        verify(purchasingRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deletePurchasingById() {
        //when
        when(purchasingRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstPurchasing));
        purchasingRepository.deleteById(ID_VALUE);
        //then
        verify(purchasingRepository, times(1)).deleteById(ID_VALUE);
        PurchasingDTO purchasingDTO = purchasingService.deletePurchasingById(ID_VALUE);
        assertEquals(firstPurchasing.getId(), purchasingDTO.getId());
    }

    @Test
    void updatePurchasingById() {
        //given
        PurchasingDTO dto = new PurchasingDTO();
        dto.setId(ID_VALUE);
        //when
        when(purchasingRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstPurchasing));
        when(purchasingRepository.save(firstPurchasing)).thenReturn(firstPurchasing);
        PurchasingDTO updatePurchasing = purchasingService.updatePurchasingById(dto, ID_VALUE);
        //then
        assertNotNull(updatePurchasing);
        assertEquals(firstPurchasing.getId(), updatePurchasing.getId());
        verify(purchasingRepository, times(1)).save(firstPurchasing);
    }

    @Test
    void createPurchasing() {
        //given
        PurchasingDTO dto = new PurchasingDTO();
        dto.setId(ID_VALUE);
        //when
        when(purchasingRepository.save(firstPurchasing)).thenReturn(firstPurchasing);
        PurchasingDTO purchasingDTO = purchasingService.createPurchasing(dto);
        //then
        assertNotNull(firstPurchasing);
        assertEquals(firstPurchasing.getId(), purchasingDTO.getId());
    }
}
