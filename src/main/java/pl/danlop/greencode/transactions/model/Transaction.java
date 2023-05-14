package pl.danlop.greencode.transactions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String debitAccount;
    private String creditAccount;
    private BigDecimal amount;

}
