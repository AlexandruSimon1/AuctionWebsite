package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.dto.UserDTO;
import com.auctionwebsite.model.Bidding;
import com.auctionwebsite.model.User;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDto(User user, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    User fromUserDto(UserDTO userDTO, @Context NotificatorMappingContext context);

    List<UserDTO> toUserDto(List<User> user, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    List<User> fromUserDto(List<UserDTO> userDTOList, @Context NotificatorMappingContext context);
}
