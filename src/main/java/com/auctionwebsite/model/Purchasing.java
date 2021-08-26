package com.auctionwebsite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
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
@Table(name = "purchasing")
public class Purchasing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne( cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne( cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Auction auction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchasing)) return false;
        Purchasing purchasing = (Purchasing) o;
        return Objects.equal(getId(), purchasing.getId()) &&
                Objects.equal(getAuction(), purchasing.getAuction()) &&
                Objects.equal(getUser(), purchasing.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getAuction(), getUser());
    }
}
