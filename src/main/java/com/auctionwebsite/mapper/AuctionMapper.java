package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.AuctionDTO;
import com.auctionwebsite.model.Auction;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuctionMapper {
    AuctionMapper INSTANCE = Mappers.getMapper(AuctionMapper.class);

    AuctionDTO toAuctionDto(Auction auction, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Auction fromAuctionDto(AuctionDTO auctionDTO, @Context NotificatorMappingContext context);
}
