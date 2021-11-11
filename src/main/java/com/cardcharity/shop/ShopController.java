package com.cardcharity.shop;

import com.cardcharity.exception.QueryException;
import com.cardcharity.exception.ServerException;
import com.cardcharity.owner.Owner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/admin/shop")
@SecurityRequirement(name = "admin")

public class ShopController {
    @Autowired
    ShopRepository dao;

    @GetMapping("/logo/{id}")
    public void getLogo(@PathVariable String id, HttpServletResponse response) throws QueryException {
        String shopName = "shopLogo_" + id + ".jpg";
        File file = new File("src/resources/logos/"+shopName);
        try {
            BufferedImage image = ImageIO.read(file);
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            throw new QueryException("this shop does not exist");
        }
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
    public void postOwner(@Valid @RequestBody Shop shop) throws ServerException {
        dao.save(shop);
    }
}
