package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.AuctionDTO;
import com.auctionwebsite.mapper.AuctionMapper;
import com.auctionwebsite.mapper.NotificatorMappingContext;
import com.auctionwebsite.model.Auction;
import com.auctionwebsite.repository.AuctionRepository;
import com.auctionwebsite.service.AmazonService;
import com.auctionwebsite.service.AuctionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class AmazonServiceImpl implements AmazonService {
    private final String bucket = "photos-bucket-simon";
    private final FileStore fileStore;
    private final AuctionRepository repository;
    private final AuctionService auctionService;

    @Override
    public AuctionDTO saveFile(Integer id, MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }
        String path = String.format("%s/%s", bucket, UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        AuctionDTO searchAuction = auctionService.getAuctionById(id);
           try {
               //get file metadata
               Map<String, String> metadata = new HashMap<>();
               metadata.put("Content-Type", file.getContentType());
               metadata.put("Content-Length", String.valueOf(file.getSize()));
               fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
               searchAuction.setPhotos(fileName);
               searchAuction.setImagePath(path);
               auctionService.updateAuctionById(searchAuction, id);
           } catch (IOException e) {
               throw new IllegalStateException("Failed to upload file", e);
           }
        Auction auction = repository.findAuctionByPhotos(searchAuction.getTitle());
        return AuctionMapper.INSTANCE.toAuctionDto(auction, new NotificatorMappingContext());
    }

    @Override
    public byte[] downloadImage(Integer id) {
        Auction auction = repository.findById(id).get();
        return fileStore.download(auction.getImagePath(), auction.getPhotos());
    }
}
