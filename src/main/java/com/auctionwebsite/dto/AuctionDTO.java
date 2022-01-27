package com.auctionwebsite.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class AuctionDTO {
    private int id;
    private String title;
    private String description;
    private String photos;
    private int minimumPrice;
    private int buyNow;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
    private CategoryDTO category;
}
