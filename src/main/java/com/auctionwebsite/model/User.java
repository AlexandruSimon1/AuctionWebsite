package com.auctionwebsite.model;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
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
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Purchasing> purchasingList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bidding> biddingList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equal(getId(), user.getId()) &&
                Objects.equal(getAddress(), user.getAddress()) &&
                Objects.equal(getBiddingList(), user.getBiddingList()) &&
                Objects.equal(getPurchasingList(), user.getPurchasingList());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getAddress(), getBiddingList(), getPurchasingList());
    }
}
