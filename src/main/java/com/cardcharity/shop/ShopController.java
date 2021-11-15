package com.cardcharity.shop;

import com.cardcharity.exception.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/admin/shop")

public class ShopController {
    @Autowired
    ShopRepository dao;

    @GetMapping("{id}/logo")
    public void getLogo(@PathVariable String id, HttpServletResponse response) throws QueryException {
        String logoFileName = "shopLogo_" + id + ".jpg";
        InputStream logoInputStream = ShopController.class.getResourceAsStream("/logos/"+logoFileName);
        try {
            BufferedImage image = ImageIO.read(logoInputStream);
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            throw new QueryException("Logo not found - does this shop exist?");
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
    public void post(@RequestBody Shop shop){
        dao.save(shop);
    }
}
