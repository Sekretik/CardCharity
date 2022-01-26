package com.cardcharity.history;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/history")
@SecurityRequirement(name = "admin")
@CrossOrigin
public class HistoryController {
    @Autowired
    HistoryDAO historyDAO;

    @GetMapping
    public List<HistoryWrapper> getHistory(@RequestParam(required = false) Long cardId,
                                           @RequestParam(required = false) Long customerId,
                                           @RequestParam(required = false) String stringStartDate,
                                           @RequestParam(required = false) String stringEndDate){
        return historyDAO.findAllByCardUser(cardId,customerId,startDate,endDate);
    }
}
