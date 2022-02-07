package com.auctionwebsite.controller;

import com.auctionwebsite.dto.AuctionDTO;
import com.auctionwebsite.service.impl.AmazonServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/s3")
public class AmazonS3Controller {
    AmazonServiceImpl service;

    @PostMapping(
            path = "/upload/{auctionId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuctionDTO> saveTodo(@PathVariable("auctionId") Integer id,
                                               @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(service.saveFile(id, file), HttpStatus.OK);
    }

    @GetMapping("/download/{auctionId}")
    public byte[] downloadTodoImage(@PathVariable("auctionId") Integer id) {
        return service.downloadImage(id);
    }
}
