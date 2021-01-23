package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.UserDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.*;
import com.auctionwebsite.model.User;
import com.auctionwebsite.repository.UserRepository;
import com.auctionwebsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().
                map(user -> UserMapper.INSTANCE.toUserDto(user, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(int id) {
        final User getUser = userRepository.findById(id).orElseThrow(() -> new ApplicationException(ExceptionType.USER_NOT_FOUND));
        return UserMapper.INSTANCE.toUserDto(getUser, new NotificatorMappingContext());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        final User createUser = UserMapper.INSTANCE.fromUserDto(userDTO, new NotificatorMappingContext());
        createUser.setCreationDate(Date.from(Instant.now()));
        final User saveUser = userRepository.save(createUser);
        return UserMapper.INSTANCE.toUserDto(saveUser, new NotificatorMappingContext());
    }

    @Override
    public UserDTO updateUserById(UserDTO userDTO, int id) {
        final User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.USER_NOT_FOUND));
        updateUser.setName(userDTO.getName());
        updateUser.setAddress(AddressMapper.INSTANCE
                .fromAddressDto(userDTO.getAddress(), new NotificatorMappingContext()));
        updateUser.setEmail(userDTO.getEmail());
        updateUser.setType(userDTO.getType());
        updateUser.setPassword(userDTO.getPassword());
        updateUser.setUserRole(userDTO.getUserRoleDTO());
        updateUser.setBiddingList(BiddingMapper.INSTANCE
                .fromBiddingsDto(userDTO.getBidding(), new NotificatorMappingContext()));
        updateUser.setPurchasingList(PurchasingMapper.INSTANCE
                .fromPurchasesDto(userDTO.getPurchasing(), new NotificatorMappingContext()));
        userRepository.save(updateUser);
        return UserMapper.INSTANCE.toUserDto(updateUser, new NotificatorMappingContext());
    }

    @Override
    public UserDTO deleteUserById(int id) {
        final User deleteUser = userRepository.findById(id)
                .orElseThrow(()-> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        userRepository.delete(deleteUser);
        return UserMapper.INSTANCE.toUserDto(deleteUser, new NotificatorMappingContext());
    }
}
