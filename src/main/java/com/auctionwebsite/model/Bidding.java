package com.auctionwebsite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    @OneToOne(mappedBy = "bidding", cascade = CascadeType.ALL)
    private User user;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false,name = "id", referencedColumnName = "id")
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
