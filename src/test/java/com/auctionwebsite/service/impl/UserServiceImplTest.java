package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.AddressDTO;
import com.auctionwebsite.dto.UserDTO;
import com.auctionwebsite.model.Address;
import com.auctionwebsite.model.User;
import com.auctionwebsite.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final int ID_VALUE = 1;
    private User firstUser;
    private User secondUser;
    private Address address;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
        List<Address> addresses = new ArrayList<>();
        address = new Address();
        address.setId(1);
        address.setCity("Douala");
        address.setProvince("Cameron");
        address.setAddress("United");

        addresses.add(address);

        firstUser = new User();
        firstUser.setId(ID_VALUE);
        firstUser.setUsername("Max Cameron");
        firstUser.setType("user");
        firstUser.setAddresses(addresses);
        firstUser.setEmail("max@cameron.com");
        firstUser.setPassword("test");

        secondUser = new User();
        secondUser.setId(ID_VALUE);
        secondUser.setUsername("Max Cameron");
        secondUser.setType("user");
        secondUser.setAddresses(addresses);
        secondUser.setEmail("max@cameron.com");
        secondUser.setPassword("test");
    }

    @Test
    void getAllUsers() {
        //given
        List<User> getAllUsers = new ArrayList<>();
        getAllUsers.add(firstUser);
        getAllUsers.add(secondUser);
        //when
        when(userRepository.findAll()).thenReturn(getAllUsers);
        List<UserDTO> userDTOS = userService.getAllUsers();
        //then
        assertEquals(getAllUsers.size(), userDTOS.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
        //when
        when(userRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstUser));
        UserDTO userDTO = userService.getUserById(ID_VALUE);
        //then
        assertEquals(firstUser.getId(), userDTO.getId());
        assertEquals(firstUser.getEmail(), userDTO.getEmail());
        assertEquals(firstUser.getUsername(), userDTO.getUsername());
        assertEquals(firstUser.getType(), userDTO.getType());
        verify(userRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteUserById() {
        //when
        when(userRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstUser));
        userRepository.deleteById(ID_VALUE);
        //then
        verify(userRepository, times(1)).deleteById(ID_VALUE);
        UserDTO userDTO = userService.deleteUserById(ID_VALUE);
        assertEquals(firstUser.getId(), userDTO.getId());
        assertEquals(firstUser.getEmail(), userDTO.getEmail());
        assertEquals(firstUser.getUsername(), userDTO.getUsername());
        assertEquals(firstUser.getType(), userDTO.getType());
    }

    @Test
    void updateUserById() {
        //given
        UserDTO dto = new UserDTO();
        dto.setId(ID_VALUE);
        dto.setUsername("Max Cameron");
        dto.setType("user");
        dto.setEmail("max@cameron.com");
        //when
        when(userRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstUser));
        when(userRepository.save(firstUser)).thenReturn(firstUser);
        UserDTO updatedUser = userService.updateUserById(dto, ID_VALUE);
        //then
        assertNotNull(updatedUser);
        assertEquals(firstUser.getId(), updatedUser.getId());
        assertEquals(firstUser.getUsername(), updatedUser.getUsername());
        assertEquals(firstUser.getType(), updatedUser.getType());
        assertEquals(firstUser.getEmail(), updatedUser.getEmail());
        verify(userRepository, times(1)).save(firstUser);
    }

    @Test
    void createUser() {
        List<AddressDTO> addressDTOS = new ArrayList<>();
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1);
        addressDTO.setCity("Douala");
        addressDTO.setProvince("Cameron");
        addressDTO.setAddress("United");
        addressDTOS.add(addressDTO);
        UserDTO dto = new UserDTO();
        dto.setId(ID_VALUE);
        dto.setUsername("Max Cameron");
        dto.setType("user");
        dto.setEmail("max@cameron.com");
        dto.setPassword("test");
        dto.setAddresses(addressDTOS);

        when(userRepository.save(firstUser)).thenReturn(firstUser);
        UserDTO userDTO = userService.createUser(dto);

        assertNotNull(firstUser);
        assertEquals(firstUser.getId(), userDTO.getId());
        assertEquals(firstUser.getUsername(), userDTO.getUsername());
        assertEquals(firstUser.getType(), userDTO.getType());
        assertEquals(firstUser.getEmail(), userDTO.getEmail());
    }
}
