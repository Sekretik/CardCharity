package com.cardcharity.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/{id}")
    public void /*User*/ getId(@PathVariable long id){
        //userDAO.get(id) - return User model
    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }

}
