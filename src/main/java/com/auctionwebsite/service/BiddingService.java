package com.auctionwebsite.service;

import com.auctionwebsite.dto.BiddingDTO;

import java.util.List;

public interface BiddingService {
    List<BiddingDTO> getAllBiddings();

    BiddingDTO getBiddingById(int id);

    BiddingDTO createBidding(BiddingDTO biddingDTO);

    BiddingDTO updateBiddingById(BiddingDTO biddingDTO, int id);

    BiddingDTO deleteBiddingById(int id);

    List<BiddingDTO> findBiddingByUserId(int id);
}
