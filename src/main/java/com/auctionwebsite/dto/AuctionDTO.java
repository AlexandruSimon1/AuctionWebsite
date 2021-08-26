package com.auctionwebsite.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuctionDTO {
    private int id;
    private String title;
    private String description;
    private String photos;
    private int minimumPrice;
    private int buyNow;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CategoryDTO category;
}
