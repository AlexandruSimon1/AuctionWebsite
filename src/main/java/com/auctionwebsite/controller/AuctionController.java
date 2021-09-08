package com.auctionwebsite.controller;

import com.auctionwebsite.dto.AuctionDTO;
import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.dto.PurchasingDTO;
import com.auctionwebsite.service.AuctionService;
import com.auctionwebsite.service.BiddingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource("application-${spring.profiles.active}.properties")
@CrossOrigin(origins = "${ui.url.origin}")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/auctions")
public class AuctionController {
    private final AuctionService auctionService;
    private final BiddingService biddingService;

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
    @PatchMapping(value = "/{auctionId}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/recently")
    public List<AuctionDTO> findRecentlyAddedAuctions(){
        return auctionService.getAllAuctions()
                .stream()
                .sorted(Comparator.comparing(AuctionDTO::getStartDate).reversed())
                .limit(6)
                .collect(Collectors.toList());
    }

    @GetMapping("/ending")
    public List<AuctionDTO> findEndingAuctions(){
        return auctionService.getAllAuctions()
                .stream()
                .sorted(Comparator.comparing(AuctionDTO::getEndDate))
                .filter(auction -> auction.getEndDate().isAfter(LocalDateTime.ofInstant(Instant.now(),  ZoneId.of("Europe/Bucharest"))))
                .limit(6)
                .collect(Collectors.toList());
    }

    @GetMapping("/recentlyEnded")
    public List<AuctionDTO> findRecentlyEndedAuctions(){
        return auctionService.getAllAuctions()
                .stream()
                .sorted(Comparator.comparing(AuctionDTO::getEndDate).reversed())
                .filter(auction -> auction.getEndDate().isBefore(LocalDateTime.ofInstant(Instant.now(),  ZoneId.of("Europe/Bucharest"))))
                .limit(6)
                .collect(Collectors.toList());
    }

    //Mapping name
    @GetMapping(value = "/{auctionId}/bidding")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<BiddingDTO> getBiddingByAuctionId(@PathVariable int auctionId) {
        return biddingService.findAllBiddingByAuctionId(auctionId);
    }
}
