package com.cardcharity.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
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
