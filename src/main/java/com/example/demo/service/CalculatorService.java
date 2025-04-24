package com.example.demo.service;

import com.example.demo.dto.CalculatorDTO;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public int plusTwoNumbers(CalculatorDTO calculatorDTO) {

        return calculatorDTO.getNum1() + calculatorDTO.getNum2();
    }
}
