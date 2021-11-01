package com.cardcharity.card;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/card")
@SecurityRequirement(name = "admin")
public class CardController {
    @GetMapping("/test")
    public String test2 (){
        return "test";
    }

    @GetMapping
    public String tes1t (){
        return "test1";
    }
}
