package com.example.testboot;

import com.example.testboot.model.TransferBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

// слой контроллера
@Slf4j // логирование
@Component // создаем бин данного класса. Родитель для @Controller, Service, Repository
@RestController("/balance") // указывает, что будет тело ответа у методов данного контроллера.(начальный путь, который будет учитываться)
//@AllArgsConstructor // позволяет инжектить все поля данного класса без конструктора
public class BalanceController {

    private final BankService bankService;

    public BalanceController(BankService bankService){
        this.bankService = bankService;
    }

    /** Узнать баланс */
    @GetMapping("/{accountid}")
    public BigDecimal getBalance(@PathVariable Long accountid){
        return bankService.getBalance(accountid);
    }

    /** Положить деньги на счет*/
    @PostMapping("/addmoney")
    public BigDecimal addMoney(@RequestBody TransferBalance addMoney){
//        System.out.println("Перешёл по ссылки /addmoney : getTo=" + addMoney.getTo() + ", .getAmount=" + addMoney.getAmount());
        return bankService.addMoney(addMoney.getTo(), addMoney.getAmount());
    }


    /** Перевод денег */
    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferBalance transfer){
        bankService.makeTransfer(transfer);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handle(IllegalArgumentException e) {
        log.error("looooog: " + e.getMessage());
        return "Вылетел эксепшен в классе: " + this.getClass();
    }
}
