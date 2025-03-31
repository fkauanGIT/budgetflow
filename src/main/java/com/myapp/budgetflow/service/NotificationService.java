package com.myapp.budgetflow.service;

import com.myapp.budgetflow.model.debt.Debt;
import com.myapp.budgetflow.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNotificationExpiration(Debt debt) {
        User usuario = debt.getUser();
        String mensagem = String.format(
                "Olá %s, sua dívida '%s' no valor de R$%.2f vence em %s.",
                usuario.getUsername(),
                debt.getDescription(),
                debt.getValue(),
                debt.getDueDate()
        );

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(usuario.getEmail());
        email.setSubject("Lembrete de dívida próxima do vencimento");
        email.setText(mensagem);

        javaMailSender.send(email);}
}