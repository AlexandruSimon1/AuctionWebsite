package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.AddressDTO;
import com.auctionwebsite.model.Address;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO toAddressDto(Address address, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Address fromAddressDto(AddressDTO addressDTO, @Context NotificatorMappingContext context);

    List<AddressDTO> toAddressDto(List<Address> address, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    List<Address> fromAddressDto(List<AddressDTO> addressDTO, @Context NotificatorMappingContext context);
}
