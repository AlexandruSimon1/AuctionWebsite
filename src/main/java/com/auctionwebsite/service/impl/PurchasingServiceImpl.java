package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.PurchasingDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.*;
import com.auctionwebsite.model.Purchasing;
import com.auctionwebsite.repository.PurchasingRepository;
import com.auctionwebsite.service.PurchasingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class PurchasingServiceImpl implements PurchasingService {
    private final PurchasingRepository purchasingRepository;

    @Override
    public List<PurchasingDTO> getAllPurchases() {
        return purchasingRepository.findAll().stream()
                .map(purchases -> PurchasingMapper.INSTANCE.toPurchasingDto(purchases, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public PurchasingDTO getPurchasingById(int id) {
        final Purchasing getPurchasing = purchasingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.PURCHASING_NOT_FOUND));
        return PurchasingMapper.INSTANCE.toPurchasingDto(getPurchasing, new NotificatorMappingContext());
    }

    @Override
    public PurchasingDTO createPurchasing(PurchasingDTO purchasingDTO) {
        final Purchasing createPurchasing = PurchasingMapper.INSTANCE.fromPurchasingDto(purchasingDTO, new NotificatorMappingContext());
        final Purchasing savePurchasing = purchasingRepository.save(createPurchasing);
        return PurchasingMapper.INSTANCE.toPurchasingDto(savePurchasing, new NotificatorMappingContext());
    }

    @Override
    public PurchasingDTO updatePurchasingById(PurchasingDTO purchasingDTO, int id) {
        final Purchasing updatePurchasing = purchasingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.PURCHASING_NOT_FOUND));
        updatePurchasing.setUser(UserMapper.INSTANCE.fromUserDto(purchasingDTO.getUser(), new NotificatorMappingContext()));
        updatePurchasing.setAuction(AuctionMapper.INSTANCE.fromAuctionDto(purchasingDTO.getAuction(), new NotificatorMappingContext()));
        purchasingRepository.save(updatePurchasing);
        return PurchasingMapper.INSTANCE.toPurchasingDto(updatePurchasing, new NotificatorMappingContext());
    }

    @Override
    public PurchasingDTO deletePurchasingById(int id) {
        final Purchasing deletePurchasing = purchasingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.PURCHASING_NOT_FOUND));
        purchasingRepository.delete(deletePurchasing);
        return PurchasingMapper.INSTANCE.toPurchasingDto(deletePurchasing, new NotificatorMappingContext());
    }
}
