package com.example.inventory.controller;

import com.example.inventory.dto.jsonapi.JsonApiResponse;
import com.example.inventory.dto.purchase.PurchaseRequest;
import com.example.inventory.dto.purchase.PurchaseResult;
import com.example.inventory.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public JsonApiResponse<PurchaseResult> purchase(
            @Valid @RequestBody PurchaseRequest request
    ) {
        PurchaseResult result = purchaseService.purchase(
                request.getProductId(),
                request.getQuantity()
        );

        return JsonApiResponse.of(
                "purchase",
                result.getProductId().toString(),
                result
        );
    }
}
