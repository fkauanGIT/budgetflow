package com.myapp.budgetflow.model.debt;

import com.myapp.budgetflow.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "debts")
@Data
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debt_id")
    private Long id;

    private String title;
    private String description;
    private BigDecimal value;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
