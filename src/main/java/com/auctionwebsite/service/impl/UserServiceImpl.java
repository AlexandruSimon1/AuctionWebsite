package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.UserDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.*;
import com.auctionwebsite.model.Address;
import com.auctionwebsite.model.Role;
import com.auctionwebsite.model.User;
import com.auctionwebsite.repository.AddressRepository;
import com.auctionwebsite.repository.UserRepository;
import com.auctionwebsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private AddressRepository addressRepository;
    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        final User createUser = new User();
        final List<Address> createAddress = AddressMapper.INSTANCE.fromAddressDto(userDTO.getAddresses(), new NotificatorMappingContext());
        final Set<Role> createRole= RoleMapper.INSTANCE.fromRolesDTO(userDTO.getRole(),new NotificatorMappingContext());
        createUser.setUsername(userDTO.getUsername());
        createUser.setFirstName(userDTO.getFirstName());
        createUser.setLastName(userDTO.getLastName());
        createUser.setEmail(userDTO.getEmail());
        createUser.setType(userDTO.getType());
        createUser.setPassword(encoder.encode(userDTO.getPassword()));
        createUser.setRoles(createRole);
        createUser.setCreationDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Bucharest")));
        final User saveUser = userRepository.save(createUser);
        for (Address address : createAddress) {
            address.setUser(saveUser);
            addressRepository.save(address);
        }
        return UserMapper.INSTANCE.toUserDto(saveUser, new NotificatorMappingContext());
    }

    @Override
    public UserDTO updateUserById(UserDTO userDTO, int id) {
        final User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.USER_NOT_FOUND));
        final List<Address> updateOrCreateAddress = AddressMapper.INSTANCE.fromAddressDto(userDTO.getAddresses(), new NotificatorMappingContext());
        final Set<Role> createRole= RoleMapper.INSTANCE.fromRolesDTO(userDTO.getRole(),new NotificatorMappingContext());
        updateUser.setUsername(userDTO.getUsername());
        updateUser.setFirstName(userDTO.getFirstName());
        updateUser.setLastName(userDTO.getLastName());
        updateUser.setEmail(userDTO.getEmail());
        updateUser.setType(userDTO.getType());
        updateUser.setPassword(encoder.encode(userDTO.getPassword()));
        updateUser.setRoles(createRole);
        final User user = userRepository.save(updateUser);
        for (Address address : updateOrCreateAddress) {
            address.setUser(user);
            addressRepository.save(address);
        }
        return UserMapper.INSTANCE.toUserDto(updateUser, new NotificatorMappingContext());
    }

    @Override
    public UserDTO deleteUserById(int id) {
        final User deleteUser = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        userRepository.delete(deleteUser);
        return UserMapper.INSTANCE.toUserDto(deleteUser, new NotificatorMappingContext());
    }

    @Override
    public UserDTO getUserByEmail(String email) throws Exception{
        final User user = userRepository.findUserByEmail(email);

        return UserMapper.INSTANCE.toUserDto(user,new NotificatorMappingContext());
    }
}
