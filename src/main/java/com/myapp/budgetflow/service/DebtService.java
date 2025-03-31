package com.myapp.budgetflow.service;

import com.myapp.budgetflow.dto.DebtDTO;
import com.myapp.budgetflow.model.debt.Debt;
import com.myapp.budgetflow.model.debt.Status;
import com.myapp.budgetflow.model.user.User;
import com.myapp.budgetflow.repository.DebtRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DebtService {

    @Autowired
    private DebtRepository debtRepository;
    private NotificationService notificationService;

    @Transactional
    public Debt createDebt(DebtDTO debtDTO, User user) {
        Debt debt = new Debt();
        debt.setDescription(debtDTO.getDescription());
        debt.setValue(debtDTO.getValue());
        debt.setDueDate(debtDTO.getDueDate());
        debt.setStatus(Status.PENDENTE);
        debt.setUser(user);

        Debt newDebt = debtRepository.save(debt);

        checkDueDueNext(newDebt);

        return newDebt;
    }

    @Transactional
    public Debt updateDebt(Long id, DebtDTO dividaDTO, User user) {
        Debt divida = debtRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Dívida não encontrada ou não pertence ao usuário"));

        divida.setDescription(dividaDTO.getDescription());
        divida.setValue(dividaDTO.getValue());
        divida.setDueDate(dividaDTO.getDueDate());

        return debtRepository.save(divida);
    }

    @Transactional
    public void deleteDebt(Long id, User user) {
        Debt divida = debtRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Dívida não encontrada ou não pertence ao usuário"));
        debtRepository.delete(divida);
    }

    private void checkDueDueNext(Debt debt) {
        if (debt.getDueDate().isBefore(LocalDate.now().plusDays(3))) {
            notificationService.sendNotificationExpiration(debt);
        }
    }
}
