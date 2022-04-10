package com.cardcharity.history;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/history")
@SecurityRequirement(name = "admin")
@CrossOrigin
public class HistoryController {
    @Autowired
    HistoryDAO historyDAO;

    @GetMapping
    public List<History> getHistory(@RequestParam(required = false) Long cardId,
                                           @RequestParam(required = false) Long customerId,
                                           @RequestParam(required = false) String startDateString,
                                           @RequestParam(required = false) String endDateString){
        return historyDAO.findAllByCardUserDate(cardId,customerId,
                startDateString == null || startDateString.isEmpty() ? null : LocalDate.parse(startDateString),
                endDateString == null || endDateString.isEmpty() ? null : LocalDate.parse(endDateString));
    }
}
