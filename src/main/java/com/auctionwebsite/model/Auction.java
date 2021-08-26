package com.auctionwebsite.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auction")
public class Auction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "photos")
    private String photos;
    @Column(name = "minimum_price")
    private int minimumPrice;
    @Column(name = "buy_now")
    private int buyNow;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @OneToOne(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private Category category;
    @OneToMany(mappedBy = "auction",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bidding> biddingList;
    @OneToOne(mappedBy = "auction",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Purchasing purchasing;
}
