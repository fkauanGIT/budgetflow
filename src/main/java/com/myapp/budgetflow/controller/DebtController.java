package com.myapp.budgetflow.controller;

import com.myapp.budgetflow.model.Customer;
import com.myapp.budgetflow.model.Debt;
import com.myapp.budgetflow.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debts")
public class DebtController {

    @Autowired
    private DebtService debtService;

    @PostMapping
    public ResponseEntity<Debt> createDebt(
            @RequestBody Debt debt,
            @AuthenticationPrincipal Customer loggedCustomer) {

        Debt createdDebt = debtService.createDebt(debt, loggedCustomer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDebt.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdDebt);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Debt> updateDebt(
            @PathVariable Long id,
            @RequestBody Debt debt,
            @AuthenticationPrincipal Customer loggedCustomer) {

        Debt updatedDebt = debtService.updateDebt(id, debt, loggedCustomer);
        return ResponseEntity.ok(updatedDebt);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebt(
            @PathVariable Long id,
            @AuthenticationPrincipal Customer loggedCustomer) {

        debtService.deleteDebt(id, loggedCustomer);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<Debt>> getAllUserDebts(
            @AuthenticationPrincipal Customer loggedCustomer) {

        List<Debt> debts = debtService.listAllByUser(loggedCustomer.getId());
        return ResponseEntity.ok(debts);
    }
}