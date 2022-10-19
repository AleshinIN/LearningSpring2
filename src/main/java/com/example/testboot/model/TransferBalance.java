package com.example.testboot.model;

import lombok.Data;

import java.math.BigDecimal;

// DTO(дата трансфер обжект) - объект, который предназначен для переноса данных из одного места в другое
@Data // разу генерирует конструктор, геттер, сеттер, иквал и хешкод
public class TransferBalance {
    /** от кого переводят */
    private Long from;

    /** кому переводят */
    private Long to;

    /** сколько переводят */
    private BigDecimal amount;

}
