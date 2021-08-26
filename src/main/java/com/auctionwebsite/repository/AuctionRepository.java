package com.auctionwebsite.repository;

import com.auctionwebsite.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//JpaRepository in one of the repository that is providing the CRUD methods for our application
@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    List<Auction> findAllAuctionsByCategoryId(int categoryId);
}
