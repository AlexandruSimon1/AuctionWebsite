package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.PurchasingDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.*;
import com.auctionwebsite.model.*;
import com.auctionwebsite.repository.AddressRepository;
import com.auctionwebsite.repository.AuctionRepository;
import com.auctionwebsite.repository.PurchasingRepository;
import com.auctionwebsite.repository.UserRepository;
import com.auctionwebsite.service.PurchasingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class PurchasingServiceImpl implements PurchasingService {
    private final PurchasingRepository purchasingRepository;
    private AuctionRepository auctionRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    public PurchasingServiceImpl(PurchasingRepository purchasingRepository) {
        this.purchasingRepository = purchasingRepository;
    }

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
        final Purchasing createPurchasing = new Purchasing();
        final User existingUser = new User();
        final Auction existingAuction = AuctionMapper.INSTANCE.fromAuctionDto(purchasingDTO.getAuction(), new NotificatorMappingContext());
        final List<Address> createAddress = AddressMapper.INSTANCE.fromAddressDto(purchasingDTO.getUser().getAddresses(), new NotificatorMappingContext());
        final Set<Role> createRole= RoleMapper.INSTANCE.fromRolesDTO(purchasingDTO.getUser().getRole(),new NotificatorMappingContext());
        auctionRepository.findById(purchasingDTO.getAuction().getId()).orElseThrow(() -> new ApplicationException(ExceptionType.AUCTION_NOT_FOUND));
        userRepository.findById(purchasingDTO.getUser().getId()).orElseThrow(() -> new ApplicationException(ExceptionType.USER_NOT_FOUND));
        for (Address address : createAddress) {
            addressRepository.findById(address.getId()).orElseThrow(() -> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        }
        if (auctionRepository.findById(purchasingDTO.getAuction().getId()).isPresent()) {
            final Auction saveAuction = auctionRepository.save(existingAuction);
            createPurchasing.setAuction(saveAuction);
        }
        if (userRepository.findById(purchasingDTO.getUser().getId()).isPresent()) {
            existingUser.setId(purchasingDTO.getUser().getId());
            existingUser.setUsername(purchasingDTO.getUser().getUsername());
            existingUser.setEmail(purchasingDTO.getUser().getEmail());
            existingUser.setType(purchasingDTO.getUser().getType());
            existingUser.setPassword(purchasingDTO.getUser().getPassword());
            existingUser.setRoles(createRole);
            existingUser.setCreationDate(purchasingDTO.getUser().getCreationDate());
            existingUser.setAddresses(createAddress);
            final User saveUser = userRepository.save(existingUser);
            createPurchasing.setUser(saveUser);
            for (Address address : createAddress) {
                address.setUser(saveUser);
                addressRepository.save(address);
            }
        }
        purchasingRepository.save(createPurchasing);
        return PurchasingMapper.INSTANCE.toPurchasingDto(createPurchasing, new NotificatorMappingContext());
    }

    @Override
    public PurchasingDTO updatePurchasingById(PurchasingDTO purchasingDTO, int id) {
        final Purchasing updatePurchasing = purchasingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.PURCHASING_NOT_FOUND));
        final User updateUser = new User();
        final List<Address> createAddress = AddressMapper.INSTANCE.fromAddressDto(purchasingDTO.getUser().getAddresses(), new NotificatorMappingContext());
        final Auction existingAuction = AuctionMapper.INSTANCE.fromAuctionDto(purchasingDTO.getAuction(), new NotificatorMappingContext());
        final Set<Role> updateRole = RoleMapper.INSTANCE.fromRolesDTO(purchasingDTO.getUser().getRole(),new NotificatorMappingContext());
        auctionRepository.findById(purchasingDTO.getAuction().getId()).orElseThrow(() -> new ApplicationException(ExceptionType.AUCTION_NOT_FOUND));
        userRepository.findById(purchasingDTO.getUser().getId()).orElseThrow(() -> new ApplicationException(ExceptionType.USER_NOT_FOUND));
        for (Address address : createAddress) {
            addressRepository.findById(address.getId()).orElseThrow(() -> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        }
        if (userRepository.findById(purchasingDTO.getUser().getId()).isPresent()) {
            updateUser.setId(purchasingDTO.getUser().getId());
            updateUser.setType(purchasingDTO.getUser().getType());
            updateUser.setEmail(purchasingDTO.getUser().getEmail());
            updateUser.setPassword(purchasingDTO.getUser().getPassword());
            updateUser.setRoles(updateRole);
            updateUser.setUsername(purchasingDTO.getUser().getUsername());
            updateUser.setCreationDate(purchasingDTO.getUser().getCreationDate());
            updateUser.setAddresses(createAddress);
            updatePurchasing.setUser(updateUser);
            for (Address address : createAddress) {
                address.setUser(updateUser);
                addressRepository.save(address);
            }
        }
        if (auctionRepository.findById(purchasingDTO.getAuction().getId()).isPresent()) {
            final Auction saveAuction = auctionRepository.save(existingAuction);
            updatePurchasing.setAuction(saveAuction);
        }
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

    @Override
    public List<PurchasingDTO> findAllPurchasingByUserId(int userId) {
        return purchasingRepository.findAllPurchasingByUserId(userId).stream().map(allPurchases -> PurchasingMapper.INSTANCE.toPurchasingDto(allPurchases, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }
}
