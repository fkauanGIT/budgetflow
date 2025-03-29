package com.myapp.budgetflow.dto;

import com.myapp.budgetflow.model.debt.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DebtDTO {
    private Long debtId;
    private String title;
    private String description;
    private BigDecimal value;
    private LocalDate due_date;
    private Status status;
    private String category;
}
