package com.cardcharity.admin;

import com.cardcharity.exception.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/**")
public class AdminController {
    @GetMapping
    public Response test(){
        return new Response("true");
    }
}
