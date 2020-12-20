package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.BiddingDTO;
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

    BiddingDTO toBiddingDto(Binding binding, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Binding fromBiddingDto(BiddingDTO biddingDTO, @Context NotificatorMappingContext context);

    List<BiddingDTO> toBindingsDto(List<Binding> bindings, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    List<Binding> fromBindingsDto(List<BiddingDTO> bindingDTOList, @Context NotificatorMappingContext context);
}
