package com.auctionwebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "type")
    private String type;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Address address;
    @OneToMany (mappedBy = "user")
    private List<Purchasing> purchasingList;
    @OneToMany (mappedBy = "user")
    private List<Bidding>biddingList;
}
