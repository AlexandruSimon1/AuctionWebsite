package com.auctionwebsite.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private int id;
    private String name;
    private String description;
    private List<AuctionDTO> auctionDTOList;
}
