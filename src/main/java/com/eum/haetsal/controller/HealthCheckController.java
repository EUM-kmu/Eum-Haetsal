package com.eum.haetsal.controller;

import com.eum.haetsal.service.BankService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class HealthCheckController {
    @GetMapping()
    public String healthCheck(){
        return "ok";
    }

}
