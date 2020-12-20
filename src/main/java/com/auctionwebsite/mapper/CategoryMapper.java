package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.CategoryDTO;
import com.auctionwebsite.model.Category;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toCategoryDto(Category category, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Category fromCategoryDto(CategoryDTO categoryDTO, @Context NotificatorMappingContext context);
}
