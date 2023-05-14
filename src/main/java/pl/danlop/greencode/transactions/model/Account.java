package pl.danlop.greencode.transactions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String account;
    private Integer debitCount;
    private Integer creditCount;
    private BigDecimal balance;

}
