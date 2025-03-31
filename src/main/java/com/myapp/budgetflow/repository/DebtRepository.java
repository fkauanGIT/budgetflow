package com.myapp.budgetflow.repository;

import com.myapp.budgetflow.model.debt.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByUserId(Long userId);
    Optional<Debt> findByIdAndUserId(Long id, Long userId);
}
