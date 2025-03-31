package com.myapp.budgetflow.controller;

import com.myapp.budgetflow.dto.DebtDTO;
import com.myapp.budgetflow.model.debt.Debt;
import com.myapp.budgetflow.model.user.User;
import com.myapp.budgetflow.repository.DebtRepository;
import com.myapp.budgetflow.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/debts")
@PreAuthorize("hasRole('USER')")
public class DebtController {

    @Autowired
    private DebtService debtService;
    private DebtRepository debtRepository;


    @PostMapping
    public ResponseEntity<Debt> createDebt(
            @RequestBody DebtDTO debtDTO,
            @AuthenticationPrincipal User loggedUser) {

        Debt createdDebt = debtService.createDebt(debtDTO, loggedUser);

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
            @RequestBody DebtDTO debtDTO,
            @AuthenticationPrincipal User loggedUser) {

        Debt updatedDebt = debtService.updateDebt(id, debtDTO, loggedUser);
        return ResponseEntity.ok(updatedDebt);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebt(
            @PathVariable Long id,
            @AuthenticationPrincipal User loggedUser) {

        debtService.deleteDebt(id, loggedUser);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<Debt>> getAllUserDebts(
            @AuthenticationPrincipal User loggedUser) {

        List<Debt> debts = debtRepository.findByUserId(loggedUser.getId());
        return ResponseEntity.ok(debts);
    }
}