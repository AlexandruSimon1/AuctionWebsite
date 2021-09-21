package com.auctionwebsite.controller;

import com.auctionwebsite.dto.AddressDTO;
import com.auctionwebsite.dto.UserDTO;
import com.auctionwebsite.service.impl.UserServiceImpl;
import com.auctionwebsite.utils.ExceptionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//This type of testing is used in case when we don't have any kind of security in our REST API
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    private static final Long ID_VALUE = 1L;
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    private UserDTO firstUser;
    private UserDTO secondUser;
    private AddressDTO address;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        List<AddressDTO> addressDTOS = new ArrayList<>();
        address = new AddressDTO();
        address.setCity("Douala");
        address.setProvince("Cameron");
        address.setAddress("United");

        addressDTOS.add(address);

        firstUser = new UserDTO();
        firstUser.setId(ID_VALUE);
        firstUser.setUsername("Max Cameron");
        firstUser.setType("user");
        firstUser.setAddresses(addressDTOS);
        firstUser.setEmail("max@cameron.com");

        secondUser = new UserDTO();
        secondUser.setId(ID_VALUE);
        secondUser.setUsername("Max Cameron");
        secondUser.setType("user");
        secondUser.setAddresses(addressDTOS);
        secondUser.setEmail("max@cameron.com");
    }

    @Test
    void getAllUsers() throws Exception {
        //given
        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(firstUser);
        userDTOList.add(secondUser);
        //when
        when(userService.getAllUsers()).thenReturn(userDTOList);
        //then
        mockMvc.perform(get("/api/v1/users")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getUserById() throws Exception {
        //when
        when(userService.getUserById(ID_VALUE)).thenReturn(firstUser);
        //then
        this.mockMvc.perform(get("/api/v1/users/{userId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void deleteUserById() throws Exception {
        //when
        when(userService.deleteUserById(ID_VALUE)).thenReturn(firstUser);
        //then
        this.mockMvc.perform(delete("/api/v1/users/{userId}", firstUser.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(userService, times(1)).deleteUserById(ID_VALUE);
    }

    @Test
    void updateUserById() throws Exception {
        //when
        when(userService.updateUserById(any(), ID_VALUE)).thenReturn(secondUser);
        //then
        this.mockMvc.perform(put("/api/v1/users/{userId}", firstUser.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(secondUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("email", Matchers.is(secondUser.getEmail())));
    }

    @Test
    void createUser() throws Exception {
        //when
        when(userService.createUser(Mockito.any(UserDTO.class))).thenReturn(firstUser);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/users").
                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(firstUser));
        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("Max Cameron"))).
                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(firstUser)));
    }
}
