package com.auctionwebsite.controller;

import com.auctionwebsite.dto.PurchasingDTO;
import com.auctionwebsite.service.PurchasingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PropertySource("application-${spring.profiles.active}.properties")
@CrossOrigin(origins = "${ui.url.origin}")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/purchases")
public class PurchasingController {
    private final PurchasingService purchasingService;

    //Mapping name
    @GetMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<PurchasingDTO> getAllPurchases() {
        return purchasingService.getAllPurchases();
    }

    //Mapping name
    @GetMapping(value = "/{purchasingId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public PurchasingDTO getPurchasingById(@PathVariable int purchasingId) {
        return purchasingService.getPurchasingById(purchasingId);
    }

    //Mapping name
    @PostMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.CREATED)
    public PurchasingDTO createPurchasing(@RequestBody PurchasingDTO purchasingDTO) {
        return purchasingService.createPurchasing(purchasingDTO);
    }

    //Mapping name
    @PatchMapping(value = "/{purchasingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public PurchasingDTO updatePurchasingById(@PathVariable int purchasingId, @RequestBody PurchasingDTO purchasingDTO) {
        return purchasingService.updatePurchasingById(purchasingDTO, purchasingId);
    }

    //Mapping name
    @DeleteMapping(value = "/{purchasingId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PurchasingDTO deletePurchasingById(@PathVariable int purchasingId) {
        return purchasingService.deletePurchasingById(purchasingId);
    }
}
