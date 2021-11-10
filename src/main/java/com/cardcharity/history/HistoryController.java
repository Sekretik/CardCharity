package com.cardcharity.history;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/history")
@SecurityRequirement(name = "admin")
public class HistoryController {
    @Autowired
    HistoryDAO historyDAO;

    @GetMapping("/get")
    public List<HistoryWrapper> getHistory(@RequestParam(required = false) Long cardId,
                                    @RequestParam(required = false) Long customerId){
        return historyDAO.findAll(cardId,customerId);
    }
}
