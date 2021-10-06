package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.*;
import com.auctionwebsite.model.*;
import com.auctionwebsite.repository.AddressRepository;
import com.auctionwebsite.repository.AuctionRepository;
import com.auctionwebsite.repository.BiddingRepository;
import com.auctionwebsite.repository.UserRepository;
import com.auctionwebsite.service.BiddingService;
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
public class BiddingServiceImpl implements BiddingService {
    private final BiddingRepository biddingRepository;
    private AuctionRepository auctionRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    public BiddingServiceImpl(BiddingRepository biddingRepository) {
        this.biddingRepository = biddingRepository;
    }

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
        final Bidding createBidding = new Bidding();
        final User existingUser = new User();
        final Auction existingAuction = AuctionMapper.INSTANCE.fromAuctionDto(biddingDTO.getAuction(), new NotificatorMappingContext());
        final List<Address> createAddress = AddressMapper.INSTANCE.fromAddressDto(biddingDTO.getUser().getAddresses(), new NotificatorMappingContext());
        final Set<Role> createRole = RoleMapper.INSTANCE.fromRolesDTO(biddingDTO.getUser().getRole(), new NotificatorMappingContext());
        auctionRepository.findById(biddingDTO.getAuction().getId()).orElseThrow(() -> new ApplicationException(ExceptionType.AUCTION_NOT_FOUND));
        userRepository.findById(biddingDTO.getUser().getId()).orElseThrow(() -> new ApplicationException(ExceptionType.USER_NOT_FOUND));
        for (Address address : createAddress) {
            addressRepository.findById(address.getId()).orElseThrow(() -> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        }
        if (auctionRepository.findById(biddingDTO.getAuction().getId()).isPresent()) {
            final Auction saveAuction = auctionRepository.save(existingAuction);
            createBidding.setAuction(saveAuction);
        }
        if (userRepository.findById(biddingDTO.getUser().getId()).isPresent()) {
            existingUser.setId(biddingDTO.getUser().getId());
            existingUser.setUsername(biddingDTO.getUser().getUsername());
            existingUser.setFirstName(biddingDTO.getUser().getFirstName());
            existingUser.setLastName(biddingDTO.getUser().getLastName());
            existingUser.setEmail(biddingDTO.getUser().getEmail());
            existingUser.setType(biddingDTO.getUser().getType());
            existingUser.setPassword(biddingDTO.getUser().getPassword());
            existingUser.setRoles(createRole);
            existingUser.setCreationDate(biddingDTO.getUser().getCreationDate());
            existingUser.setAddresses(createAddress);
            final User saveUser = userRepository.save(existingUser);
            createBidding.setUser(saveUser);
            for (Address address : createAddress) {
                address.setUser(saveUser);
                addressRepository.save(address);
            }
        }
        biddingRepository.save(createBidding);
        return BiddingMapper.INSTANCE.toBiddingDto(createBidding, new NotificatorMappingContext());
    }

    @Override
    public BiddingDTO updateBiddingById(BiddingDTO biddingDTO, int id) {
        final Bidding updateBiding = biddingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.BIDDING_NOT_FOUND));
        final User updateUser = new User();
        final List<Address> createAddress = AddressMapper.INSTANCE.fromAddressDto(biddingDTO.getUser().getAddresses(), new NotificatorMappingContext());
        final Auction existingAuction = AuctionMapper.INSTANCE.fromAuctionDto(biddingDTO.getAuction(), new NotificatorMappingContext());
        final Set<Role> updateRole = RoleMapper.INSTANCE.fromRolesDTO(biddingDTO.getUser().getRole(), new NotificatorMappingContext());
        auctionRepository.findById(biddingDTO.getAuction().getId()).orElseThrow(() -> new ApplicationException(ExceptionType.AUCTION_NOT_FOUND));
        userRepository.findById(biddingDTO.getUser().getId()).orElseThrow(() -> new ApplicationException(ExceptionType.USER_NOT_FOUND));
        for (Address address : createAddress) {
            addressRepository.findById(address.getId()).orElseThrow(() -> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        }
        if (userRepository.findById(biddingDTO.getUser().getId()).isPresent()) {
            updateUser.setId(biddingDTO.getUser().getId());
            updateUser.setType(biddingDTO.getUser().getType());
            updateUser.setEmail(biddingDTO.getUser().getEmail());
            updateUser.setPassword(biddingDTO.getUser().getPassword());
            updateUser.setRoles(updateRole);
            updateUser.setUsername(biddingDTO.getUser().getUsername());
            updateUser.setFirstName(biddingDTO.getUser().getFirstName());
            updateUser.setLastName(biddingDTO.getUser().getLastName());
            updateUser.setCreationDate(biddingDTO.getUser().getCreationDate());
            updateUser.setAddresses(createAddress);
            updateBiding.setUser(updateUser);
            for (Address address : createAddress) {
                address.setUser(updateUser);
                addressRepository.save(address);
            }
        }
        if (auctionRepository.findById(biddingDTO.getAuction().getId()).isPresent()) {
            final Auction saveAuction = auctionRepository.save(existingAuction);
            updateBiding.setAuction(saveAuction);
        }
        biddingRepository.save(updateBiding);
        return BiddingMapper.INSTANCE.toBiddingDto(updateBiding, new NotificatorMappingContext());
    }

    @Override
    public BiddingDTO deleteBiddingById(int id) {
        final Bidding deleteBidding = biddingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.BIDDING_NOT_FOUND));
        biddingRepository.delete(deleteBidding);
        return BiddingMapper.INSTANCE.toBiddingDto(deleteBidding, new NotificatorMappingContext());
    }

    @Override
    public List<BiddingDTO> findAllBiddingByUserId(int userId) {
        return biddingRepository.findAllBiddingByUserId(userId).stream().map(allBindings -> BiddingMapper.INSTANCE.toBiddingDto(allBindings, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public List<BiddingDTO> findAllBiddingByAuctionId(int auctionId) {
        return biddingRepository.findAllBiddingByAuctionId(auctionId).stream().map(allBindings -> BiddingMapper.INSTANCE.toBiddingDto(allBindings, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }
}
