package com.auctionwebsite.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

//Creating our personal Exception
@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "Address not found"),
    AUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Auction not found"),
    BIDDING_NOT_FOUND(HttpStatus.NOT_FOUND, "Bidding not found"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Category not found"),
    PURCHASING_NOT_FOUND(HttpStatus.NOT_FOUND, "Purchasing not found");

    private final HttpStatus httpStatus;
    private final String message;

}