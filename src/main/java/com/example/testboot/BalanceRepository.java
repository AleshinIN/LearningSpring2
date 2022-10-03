package com.example.testboot;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.security.DigestException;
import java.util.HashMap;
import java.util.Map;

// слой:  дата аксес слой. Уровень общения с базой
/** Используем как хранилище данных */
@Repository
public class BalanceRepository { // сделаем хранилище для данных(чтобы не использовать SQL в данном уроке)
    /** Ключ - ID
     * Значение - сумма денег */
    private final Map<Long, BigDecimal> storage = new HashMap<>(Map.of(1L, BigDecimal.TEN));
//    private final Map<Long, BigDecimal> storage = new HashMap<>();

    public BigDecimal getBalanceForId(Long accountID) {
        return storage.get(accountID);
    }


    public void save(Long to, BigDecimal amount) {
        storage.put(to, amount);
    }


}
