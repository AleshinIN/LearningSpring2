package com.example.testboot;

import com.example.testboot.model.TransferBalance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

// слой сервисов
@Service //  фасад для некоторой бизнес логики
@AllArgsConstructor
public class BankService {

    private final BalanceRepository repository;

    public BigDecimal getBalance(Long accountid) {
        BigDecimal balance = repository.getBalanceForId(accountid);
        if (balance == null) throw new IllegalArgumentException();
        return balance;
    }
    
    public BigDecimal addMoney(Long to, BigDecimal amount) {

        BigDecimal balance = repository.getBalanceForId(to);
        if (balance == null) {
            repository.save(to, amount);
            return amount;
        } else {
            BigDecimal updateBalance = balance.add(amount);
            repository.save(to, updateBalance);
            return updateBalance;
        }

    }

    public void makeTransfer(TransferBalance transfer) {
        BigDecimal toBalance = repository.getBalanceForId(transfer.getTo());
        BigDecimal fromBalance = repository.getBalanceForId(transfer.getFrom());

        if (fromBalance == null || toBalance == null ) throw new IllegalArgumentException("Не создан получатель или отправитель");
        // 1:37:00 Если сумма меньше чем то, сколько мы хотим перевести
        if (fromBalance.longValue() < transfer.getAmount().longValue()) {
            throw new IllegalArgumentException("Перевод отклонен. Недостаточно денег на счёте");
        }

        BigDecimal updeteToBalance = toBalance.add(transfer.getAmount());
        BigDecimal updeteFromBalance = fromBalance.subtract(transfer.getAmount());
        repository.save(transfer.getTo(), updeteToBalance);
        repository.save(transfer.getFrom(), updeteFromBalance);
    }
}
