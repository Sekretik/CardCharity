package com.cardcharity.shop;

import com.cardcharity.exception.QueryException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RestController
@RequestMapping("/user/shop")
@CrossOrigin
public class ShopController {
    @Autowired
    ShopRepository dao;
    @Autowired
    Environment environment;

    @GetMapping(value = "{id}/logo")
    public @ResponseBody byte[] getImage(@PathVariable String id) throws IOException {
        String logoFileName = dao.findById(Long.valueOf(id)).get().getName().toLowerCase() + "_logo.jpg";
        InputStream in;
        try {
            in = Files.newInputStream(Path.of(environment.getProperty("shop.logo") + logoFileName));
        }catch (Exception e){
            in = Files.newInputStream(Path.of("src/resources/default_logo.jpeg"));
        }
        System.out.println(in);
        return IOUtils.toByteArray(in);
    }

    @GetMapping
    public Iterable<Shop> getShops(){
        return dao.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Shop> getShopWithID(@PathVariable Long id){
        return dao.findById(id);
    }

    @PostMapping
    public void post(@RequestBody String shopName){
        Shop shop = new Shop();
        shop.setName(shopName);
        dao.save(shop);
    }
}
