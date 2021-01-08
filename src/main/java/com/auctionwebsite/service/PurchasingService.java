package com.auctionwebsite.service;

import com.auctionwebsite.dto.PurchasingDTO;

import java.util.List;

public interface PurchasingService {
    List<PurchasingDTO> getAllPurchases();

    PurchasingDTO getPurchasingById(int id);

    PurchasingDTO createPurchasing(PurchasingDTO purchasingDTO);

    PurchasingDTO updatePurchasingById(PurchasingDTO purchasingDTO, int id);

    PurchasingDTO deletePurchasingById(int id);
}
