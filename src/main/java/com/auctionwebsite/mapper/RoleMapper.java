package com.auctionwebsite.mapper;

import com.auctionwebsite.dto.RoleDTO;
import com.auctionwebsite.model.Role;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toRoleDto(Role role, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Role fromRoleDTO(RoleDTO roleDTO, @Context NotificatorMappingContext context);

    Set<RoleDTO> toRolesDTO(Set<Role> roles, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Set<Role> fromRolesDTO(Set<RoleDTO> roleDTOS, @Context NotificatorMappingContext context);
}
