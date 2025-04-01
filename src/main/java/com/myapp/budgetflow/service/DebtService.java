package com.myapp.budgetflow.service;

import com.myapp.budgetflow.model.Customer;
import com.myapp.budgetflow.model.Debt;
import com.myapp.budgetflow.model.Status;
import com.myapp.budgetflow.repository.DebtRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebtService {

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Debt createDebt(Debt debt, Customer customer) {
        debt.setStatus(Status.PENDENTE);
        debt.setCustomer(customer);
        Debt newDebt = debtRepository.save(debt);
        checkDueDate(newDebt);
        return newDebt;
    }

    public Debt getDebtById(Long id) {
        return debtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dívida não encontrada"));
    }

    public List<Debt> listAllByUser(Long userId) {
        return debtRepository.findByCustomerId(userId);
    }

    @Transactional
    public Debt updateDebt(Long id, Debt debtDetails, Customer loggedCustomer) {
        Debt debt = getDebtById(id);
        debt.setTitle(debtDetails.getTitle());
        debt.setDescription(debtDetails.getDescription());
        debt.setValue(debtDetails.getValue());
        debt.setDueDate(debtDetails.getDueDate());
        debt.setCategory(debtDetails.getCategory());
        debt.setStatus(debtDetails.getStatus());

        return debtRepository.save(debt);
    }

    @Transactional
    public void deleteDebt(Long id, Customer loggedCustomer) {
        Debt debt = getDebtById(id);
        debtRepository.delete(debt);
    }

    private void checkDueDate(Debt debt) {
        if (debt.getDueDate().isBefore(LocalDate.now().plusDays(3))) {
            notificationService.sendNotificationExpiration(debt);
        }
    }
}
