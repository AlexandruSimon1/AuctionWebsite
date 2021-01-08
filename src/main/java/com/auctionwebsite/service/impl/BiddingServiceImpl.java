package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.*;
import com.auctionwebsite.model.Bidding;
import com.auctionwebsite.repository.BiddingRepository;
import com.auctionwebsite.service.BiddingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class BiddingServiceImpl implements BiddingService {
    private final BiddingRepository biddingRepository;

    @Override
    public List<BiddingDTO> getAllBiddings() {
        return biddingRepository.findAll().stream()
                .map(allBindings -> BiddingMapper.INSTANCE.toBiddingDto(allBindings, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public BiddingDTO getBiddingById(int id) {
        final Bidding getBidding = biddingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.BIDDING_NOT_FOUND));
        return BiddingMapper.INSTANCE.toBiddingDto(getBidding, new NotificatorMappingContext());
    }

    @Override
    public BiddingDTO createBidding(BiddingDTO biddingDTO) {
        final Bidding createBidding = BiddingMapper.INSTANCE.fromBiddingDto(biddingDTO, new NotificatorMappingContext());
        final Bidding saveBidding = biddingRepository.save(createBidding);
        return BiddingMapper.INSTANCE.toBiddingDto(saveBidding, new NotificatorMappingContext());
    }

    @Override
    public BiddingDTO updateBiddingById(BiddingDTO biddingDTO, int id) {
        final Bidding updateBiding = biddingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.BIDDING_NOT_FOUND));
        updateBiding.setUser(UserMapper.INSTANCE.fromUserDto(biddingDTO.getUser(), new NotificatorMappingContext()));
        updateBiding.setAuction(AuctionMapper.INSTANCE.fromAuctionDto(biddingDTO.getAuction(), new NotificatorMappingContext()));
        biddingRepository.save(updateBiding);
        return BiddingMapper.INSTANCE.toBiddingDto(updateBiding, new NotificatorMappingContext());
    }

    @Override
    public BiddingDTO deleteBiddingById(int id) {
        final Bidding deleteBidding = biddingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.BIDDING_NOT_FOUND));
        return BiddingMapper.INSTANCE.toBiddingDto(deleteBidding, new NotificatorMappingContext());
    }
}
