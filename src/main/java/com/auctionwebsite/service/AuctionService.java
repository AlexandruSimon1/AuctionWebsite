package com.auctionwebsite.service;

import com.auctionwebsite.dto.AuctionDTO;

import java.util.List;

public interface AuctionService {
    List<AuctionDTO> getAllAuctions();

    AuctionDTO getAuctionById(int id);

    AuctionDTO createAuction(AuctionDTO auctionDTO);

    AuctionDTO updateAuctionById(AuctionDTO auctionDTO, int id);

    AuctionDTO deleteAuctionById(int id);
}
