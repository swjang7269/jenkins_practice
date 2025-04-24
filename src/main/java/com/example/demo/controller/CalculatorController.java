package com.example.demo.controller;

import com.example.demo.dto.CalculatorDTO;
import com.example.demo.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CalculatorController {

    CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm alive!!";
    }

//    @GetMapping("/plus")
//    public ResponseEntity<CalculatorDTO> plusTwoNumbers(CalculatorDTO calculatorDTO) {
//
//        log.info("calculatorDTO: {}", calculatorDTO);
//        int result = calculatorService.plusTwoNumbers(calculatorDTO);
//        calculatorDTO.setSum(result);
//
//        return ResponseEntity
//                .ok()
//                .body(calculatorDTO);
//    }
    @PostMapping("/plus")
    public ResponseEntity<CalculatorDTO> plusTwoNumbers(@RequestBody CalculatorDTO calculatorDTO) {

        log.info("calculatorDTO: {}", calculatorDTO);
        int result = calculatorService.plusTwoNumbers(calculatorDTO);
        calculatorDTO.setSum(result);

        return ResponseEntity
                .ok()
                .body(calculatorDTO);
    }
}
