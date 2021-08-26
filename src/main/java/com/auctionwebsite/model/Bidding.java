package com.auctionwebsite.model;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bidding")
public class Bidding implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private Auction auction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bidding)) return false;
        Bidding bidding = (Bidding) o;
        return Objects.equal(getId(), bidding.getId()) &&
                Objects.equal(getAuction(), bidding.getAuction()) &&
                Objects.equal(getUser(), bidding.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getAuction(), getUser());
    }
}
