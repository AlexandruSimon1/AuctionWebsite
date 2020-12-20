package com.auctionwebsite.repository;

import com.auctionwebsite.model.Binding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository in one of the repository that is providing the CRUD methods for our application
@Repository
public interface BiddingRepository extends JpaRepository<Binding, Integer> {
}
