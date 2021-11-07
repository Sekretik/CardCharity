package com.cardcharity.history;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/history")
@SecurityRequirement(name = "admin")
public class HistoryController {
    @Autowired
    HistoryDAO historyDAO;
    @Autowired
    HistoryRepository repository;

    @GetMapping("/get")
    public List<History> getHistory(){
        return repository.findAll();
    }
}
