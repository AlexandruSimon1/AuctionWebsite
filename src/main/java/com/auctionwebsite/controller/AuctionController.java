package com.auctionwebsite.controller;

import com.auctionwebsite.dto.AuctionDTO;
import com.auctionwebsite.service.AuctionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/auctions")
public class AuctionController {
    private final AuctionService auctionService;

    //Mapping name
    @GetMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<AuctionDTO> getAllAuctions() {
        return auctionService.getAllAuctions();
    }

    //Mapping name
    @GetMapping(value = "/{auctionId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public AuctionDTO getAuctionById(@PathVariable int auctionId) {
        return auctionService.getAuctionById(auctionId);
    }

    //Mapping name
    @PostMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.CREATED)
    public AuctionDTO createAuction(@RequestBody AuctionDTO auctionDTO) {
        return auctionService.createAuction(auctionDTO);
    }

    //Mapping name
    @PutMapping(value = "/{auctionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public AuctionDTO updateAuctionById(@PathVariable int auctionId, @RequestBody AuctionDTO auctionDTO) {
        return auctionService.updateAuctionById(auctionDTO, auctionId);
    }

    //Mapping name
    @DeleteMapping(value = "/{auctionId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AuctionDTO deleteAuctionById(@PathVariable int auctionId) {
        return auctionService.deleteAuctionById(auctionId);
    }
}
