package com.auctionwebsite;

import de.dentrassi.crypto.pem.PemKeyStoreProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
public class AuctionWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionWebsiteApplication.class, args);
    }

}
