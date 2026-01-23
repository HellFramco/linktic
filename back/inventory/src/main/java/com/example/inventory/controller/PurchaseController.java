package com.example.inventory.controller;

import com.example.inventory.dto.jsonapi.JsonApiResponse;
import com.example.inventory.dto.purchase.PurchaseRequest;
import com.example.inventory.dto.purchase.PurchaseResult;
import com.example.inventory.service.NotificationService;
import com.example.inventory.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final NotificationService notificationService;

     public PurchaseController(
            PurchaseService purchaseService,
            NotificationService notificationService
    ) {
        this.purchaseService = purchaseService;
        this.notificationService = notificationService;
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

    @GetMapping("/notifications/stream")
    public SseEmitter stream() {
        return notificationService.register();
    }
}
