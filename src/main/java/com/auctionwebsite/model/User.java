package com.auctionwebsite.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private LocalDateTime creationDate;
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addresses;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Purchasing> purchasingList;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false, name = "id", referencedColumnName = "id")
    private Bidding bidding;
    @Column(name = "role")
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equal(getId(), user.getId()) &&
                Objects.equal(getAddresses(), user.getAddresses()) &&
                Objects.equal(getBidding(), user.getBidding()) &&
                Objects.equal(getPurchasingList(), user.getPurchasingList());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getAddresses(), getBidding(), getPurchasingList());
    }
}
