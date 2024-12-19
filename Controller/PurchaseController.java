package com.example.capston3.Controller;

import com.example.capston3.ApiResponse.ApiResponse;
import com.example.capston3.InDTO.PurchaseDTO_In;
import com.example.capston3.Service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping("/get")
    public ResponseEntity getAllPurchases() {
        return ResponseEntity.status(200).body(purchaseService.getAllPurchases());
    }

    @PostMapping("/add")
    public ResponseEntity addPurchase(@RequestBody @Valid PurchaseDTO_In purchaseInDTO) {
        purchaseService.addPurchase(purchaseInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("purchases added"));
    }




}
