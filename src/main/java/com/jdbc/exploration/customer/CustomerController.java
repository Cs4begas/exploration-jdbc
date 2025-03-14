package com.jdbc.exploration.customer;

import com.jdbc.exploration.CustomerService;
import com.jdbc.exploration.customer.entity.CustomerInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerInfo>> getCustomer() {
        return ResponseEntity.ok(customerService.findAllCustomerInfos());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CustomerInfo>> getCustomer(
            @RequestParam(required = true, defaultValue = "0") int page,
            @RequestParam(required = true) int size,
            @RequestParam(required = true) String sortField,
            @RequestParam(required = true) String sortDirection
    ) {
        return ResponseEntity.ok(customerService.findPageCustomerInfos(page, size, sortField, sortDirection));
    }

    @PostMapping
    public ResponseEntity<CustomerInfo> createCustomer(@RequestBody @Valid CustomerInfo customerDTO) {
        return ResponseEntity.ok(customerService.createCustomerInfo(customerDTO));
    }

    @PatchMapping("/{customerNo}")
    public ResponseEntity<CustomerInfo> updateCustomer(@RequestBody @Valid CustomerInfo customerDTO, @PathVariable String customerNo) {
        return ResponseEntity.ok(customerService.updateCustomerInfo(customerDTO, customerNo));
    }
}
