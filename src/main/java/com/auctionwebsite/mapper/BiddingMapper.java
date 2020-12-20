package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.BindingDTO;
import com.auctionwebsite.model.Binding;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BiddingMapper {
    BiddingMapper INSTANCE = Mappers.getMapper(BiddingMapper.class);

    BindingDTO toBiddingDto(Binding binding, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Binding fromBiddingDto(BindingDTO bindingDTO, @Context NotificatorMappingContext context);

    List<BindingDTO> toBindingsDto(List<Binding> bindings, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    List<Binding> fromBindingsDto(List<BindingDTO> bindingDTOList, @Context NotificatorMappingContext context);
}
