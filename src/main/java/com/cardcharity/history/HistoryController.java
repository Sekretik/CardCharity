package com.cardcharity.history;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/history")
@SecurityRequirement(name = "admin")
public class HistoryController {
}
