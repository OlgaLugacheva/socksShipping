package com.example.socksshipping.controller;

import com.example.socksshipping.exception.EmptyStockException;
import com.example.socksshipping.dto.SockShippingDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.socksshipping.model.Color;
import com.example.socksshipping.model.SocksSize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.example.socksshipping.service.SocksService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/socks")
@RequiredArgsConstructor
public class SocksController {

    private final SocksService sockService;

    @PostMapping
    public void addSocks(@Valid @RequestBody SockShippingDto sockShippingDto) {
        sockService.addSocks(sockShippingDto);
    }

    @PutMapping
    public void sellSocks(@Valid @RequestBody SockShippingDto sockShippingDto) {
        sockService.sellSocks(sockShippingDto);
    }

    @GetMapping
    public long getSocksCount(@RequestParam(required = false, name = "color") Color color,
                             @RequestParam(required = false, name = "size") SocksSize size,
                             @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                             @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        return sockService.getSockQuantity(color, size, cottonMin, cottonMax);
    }

    @DeleteMapping
    public void removeDefectiveSocks(@Valid @RequestBody SockShippingDto sockShippingDto) {
        sockService.removeDefectiveSocks(sockShippingDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyStockException.class)
    public ResponseEntity<String> handleInvalidException(EmptyStockException emptyStockException) {
        return ResponseEntity.badRequest().body(emptyStockException.getMessage());
    }

}
