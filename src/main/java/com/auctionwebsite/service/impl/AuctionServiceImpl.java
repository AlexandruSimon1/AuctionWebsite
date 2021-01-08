package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.AuctionDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.*;
import com.auctionwebsite.model.Auction;
import com.auctionwebsite.repository.AuctionRepository;
import com.auctionwebsite.service.AuctionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private static final int AUCTION_DAYS = 7;

    @Override
    public List<AuctionDTO> getAllAuctions() {
        return auctionRepository.findAll().stream()
                .map(allAuctions -> AuctionMapper.INSTANCE.toAuctionDto(allAuctions, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public AuctionDTO getAuctionById(int id) {
        final Auction getAuction = auctionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.AUCTION_NOT_FOUND));
        return AuctionMapper.INSTANCE.toAuctionDto(getAuction, new NotificatorMappingContext());
    }

    @Override
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {
        final Auction createAuction = AuctionMapper.INSTANCE.fromAuctionDto(auctionDTO, new NotificatorMappingContext());
        createAuction.setStartDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Bucharest")));
        createAuction.setEndDate(LocalDateTime.ofInstant(Instant.now().plus(AUCTION_DAYS, ChronoUnit.DAYS), ZoneId.of("Europe/Bucharest")));
        final Auction saveAuction = auctionRepository.save(createAuction);
        return AuctionMapper.INSTANCE.toAuctionDto(saveAuction, new NotificatorMappingContext());
    }

    @Override
    public AuctionDTO updateAuctionById(AuctionDTO auctionDTO, int id) {
        final Auction updateAuction = auctionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.AUCTION_NOT_FOUND));
        updateAuction.setTitle(auctionDTO.getTitle());
        updateAuction.setDescription(auctionDTO.getDescription());
        updateAuction.setPhotos(auctionDTO.getPhotos());
        updateAuction.setMinimumPrice(auctionDTO.getMinimumPrice());
        updateAuction.setBuyNow(auctionDTO.getBuyNow());
        updateAuction.setStartDate(auctionDTO.getStartDate());
        updateAuction.setEndDate(auctionDTO.getEndDate());
        updateAuction.setCategory(CategoryMapper.INSTANCE.fromCategoryDto(auctionDTO.getCategory(), new NotificatorMappingContext()));
        updateAuction.setBiddingList(BiddingMapper.INSTANCE.fromBiddingsDto(auctionDTO.getBidding(), new NotificatorMappingContext()));
        updateAuction.setPurchasing(PurchasingMapper.INSTANCE.fromPurchasingDto(auctionDTO.getPurchasing(), new NotificatorMappingContext()));
        auctionRepository.save(updateAuction);
        return AuctionMapper.INSTANCE.toAuctionDto(updateAuction, new NotificatorMappingContext());
    }

    @Override
    public AuctionDTO deleteAuctionById(int id) {
        final Auction deleteAuction = auctionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.AUCTION_NOT_FOUND));
        return AuctionMapper.INSTANCE.toAuctionDto(deleteAuction, new NotificatorMappingContext());
    }
}
