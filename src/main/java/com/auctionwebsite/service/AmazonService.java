package com.auctionwebsite.service;

import com.auctionwebsite.dto.AuctionDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonService {
    AuctionDTO saveFile(Integer id, MultipartFile file);

    byte[] downloadImage(Integer id);
}
