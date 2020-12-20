package com.auctionwebsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "address")
    private User user;
    @Column(name = "province")
    private String province;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
}
