package com.cardcharity.shop;

import com.cardcharity.exception.QueryException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@CrossOrigin
public class ShopController {
    @Autowired
    ShopRepository dao;
    @Autowired
    Environment environment;

    @GetMapping(value = "/user/shop/{id}/logo")
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

    @GetMapping("/user/shop")
    public Iterable<Shop> getShops(){
        return dao.findAll();
    }

    @GetMapping("/user/shop/{id}")
    public Optional<Shop> getShopWithID(@PathVariable Long id){
        return dao.findById(id);
    }

    @PostMapping("/admin/shop")
    @SecurityRequirement(name = "admin")
    public Shop post(@RequestBody Shop shop) {
        shop.setId(null);
        dao.save(shop);
        return shop;
    }

    @PutMapping("/admin/shop/{id}")
    @SecurityRequirement(name = "admin")
    public Shop put(@RequestBody @Valid Shop shop, @PathVariable Long id) throws Throwable {
        shop.setId(id);
        dao.findById(id).orElseThrow(new Supplier<Throwable>() {
            @Override
            public Throwable get() {
                return new QueryException("No shop with this id");
            }
        });
        dao.save(shop);
        return shop;
    }
}
