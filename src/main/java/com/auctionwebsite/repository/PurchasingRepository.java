package com.auctionwebsite.repository;

import com.auctionwebsite.model.Purchasing;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository in one of the repository that is providing the CRUD methods for our application
public interface PurchasingRepository extends JpaRepository<Purchasing, Integer> {
}
