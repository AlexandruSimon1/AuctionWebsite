package com.auctionwebsite.controller;

import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.service.BiddingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/biddings")
public class BiddingController {
    private final BiddingService biddingService;

    //Mapping name
    @GetMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<BiddingDTO> getAllBiddings() {
        return biddingService.getAllBiddings();
    }

//    //Mapping name
//    @GetMapping(value = "/{biddingId}")
//    //Response status is used for providing the status of our request
//    @ResponseStatus(HttpStatus.OK)
//    public BiddingDTO getBiddingById(@PathVariable int biddingId) {
//        return biddingService.getBiddingById(biddingId);
//    }

    //Mapping name
    @PostMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.CREATED)
    public BiddingDTO createBidding(@RequestBody BiddingDTO biddingDTO) {
        return biddingService.createBidding(biddingDTO);
    }

    //Mapping name
    @PutMapping(value = "/{biddingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public BiddingDTO updateBiddingById(@PathVariable int biddingId, @RequestBody BiddingDTO biddingDTO) {
        return biddingService.updateBiddingById(biddingDTO, biddingId);
    }

    //Mapping name
    @DeleteMapping(value = "/{biddingId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BiddingDTO deleteBiddingById(@PathVariable int biddingId) {
        return biddingService.deleteBiddingById(biddingId);
    }
    @GetMapping("/{userId}")
    public List<BiddingDTO> findBiddingByUserId(@PathVariable("usetId") int id){
        return biddingService.findBiddingByUserId(id);
    }
}
