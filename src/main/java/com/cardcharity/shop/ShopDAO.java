package com.cardcharity.shop;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ShopDAO {
    @Autowired
    ShopRepository shopRepository;

    public boolean existsById(long id) {
        return shopRepository.existsById(id);
    }

    public Optional<Shop> findById(long id) {
        return shopRepository.findById(id);
    }
}
