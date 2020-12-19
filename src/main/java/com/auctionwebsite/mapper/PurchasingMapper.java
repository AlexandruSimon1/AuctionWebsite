package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.PurchasingDTO;
import com.auctionwebsite.model.Purchasing;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchasingMapper {
    PurchasingMapper INSTANCE = Mappers.getMapper(PurchasingMapper.class);

    PurchasingDTO toPurchasingDto(Purchasing purchasing, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Purchasing fromPurchasingDto(PurchasingDTO purchasingDTO, @Context NotificatorMappingContext context);
}
