package com.cardcharity.web;

import com.cardcharity.web.exception.ServerException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public void /*User*/ getId(@PathVariable long id){
        //userDAO.get(id) - return User model
    }
    @GetMapping("/test")
    public String test(@RequestParam boolean bool) throws Exception {
        if(bool){
            throw new ServerException("loh");
        }
        return "Test Done";
    }
    @GetMapping("/get")
    public TestModel getObj(@RequestParam("id") int id, @RequestParam("name") String name){
        return new TestModel(id,name);
    }

}
