package com.springstudy.orderservice.order.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotBlank(message = "Book ISBN is required")
        String bookIsbn,
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be greater than 0")
        @Max(value = 5, message = "Quantity must be less than 5")
        Integer quantity
) {
}
