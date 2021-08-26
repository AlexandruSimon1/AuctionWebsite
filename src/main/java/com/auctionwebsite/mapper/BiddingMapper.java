package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.model.Bidding;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BiddingMapper {
    BiddingMapper INSTANCE = Mappers.getMapper(BiddingMapper.class);

    BiddingDTO toBiddingDto(Bidding bidding, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Bidding fromBiddingDto(BiddingDTO biddingDTO, @Context NotificatorMappingContext context);

    List<BiddingDTO> toBiddingsDto(List<Bidding> biddings, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    List<Bidding> fromBiddingsDto(List<BiddingDTO> biddingDTOList, @Context NotificatorMappingContext context);
}
