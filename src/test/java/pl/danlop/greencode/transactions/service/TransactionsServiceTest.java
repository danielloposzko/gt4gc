package pl.danlop.greencode.transactions.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pl.danlop.greencode.transactions.model.Account;
import pl.danlop.greencode.transactions.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author daniel
 */
class TransactionsServiceTest {

    private TransactionsService transactionsService;

    @BeforeEach
    public void setUp() {
        transactionsService = new TransactionsService();
    }

    @Test
    void testReport_noTransactions_returnsEmptyList() {
        List<Transaction> transactions = Arrays.asList();

        List<Account> result = transactionsService.report(transactions);

        assertEquals(0, result.size());
    }

    @Test
    void testReport_singleTransaction_returnsTwoAccounts() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("Account1", "Account2", BigDecimal.valueOf(100))
        );

        List<Account> result = transactionsService.report(transactions);

        assertEquals(2, result.size());

        Account debitAccount = result.get(0);
        assertEquals("Account1", debitAccount.getAccount());
        assertEquals(1, debitAccount.getDebitCount());
        assertEquals(0, debitAccount.getCreditCount());
        assertEquals(BigDecimal.valueOf(-100).setScale(2), debitAccount.getBalance());

        Account creditAccount = result.get(1);
        assertEquals("Account2", creditAccount.getAccount());
        assertEquals(0, creditAccount.getDebitCount());
        assertEquals(1, creditAccount.getCreditCount());
        assertEquals(BigDecimal.valueOf(100).setScale(2), creditAccount.getBalance());
    }

    @Test
    void testReport_multipleTransactions_returnsMultipleAccounts() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("Account1", "Account2", BigDecimal.valueOf(100)),
                new Transaction("Account2", "Account3", BigDecimal.valueOf(20))
        );

        List<Account> result = transactionsService.report(transactions);

        assertEquals(3, result.size());

        Account account1 = result.get(0);
        assertEquals("Account1", account1.getAccount());
        assertEquals(1, account1.getDebitCount());
        assertEquals(0, account1.getCreditCount());
        assertEquals(BigDecimal.valueOf(-100).setScale(2), account1.getBalance());

        Account account2 = result.get(1);
        assertEquals("Account2", account2.getAccount());
        assertEquals(1, account2.getDebitCount());
        assertEquals(1, account2.getCreditCount());
        assertEquals(BigDecimal.valueOf(80).setScale(2), account2.getBalance());

        Account account3 = result.get(2);
        assertEquals("Account3", account3.getAccount());
        assertEquals(0, account3.getDebitCount());
        assertEquals(1, account3.getCreditCount());
        assertEquals(BigDecimal.valueOf(20).setScale(2), account3.getBalance());
    }

    @Test
    void testReport_exampleRequest_returnsExampleResponse() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("32309111922661937852684864", "06105023389842834748547303", BigDecimal.valueOf(10.90)),
                new Transaction("31074318698137062235845814", "66105036543749403346524547", BigDecimal.valueOf(200.90)),
                new Transaction("66105036543749403346524547", "32309111922661937852684864", BigDecimal.valueOf(50.10))
        );

        List<Account> result = transactionsService.report(transactions);

        assertEquals(4, result.size());

        Account account1 = result.get(0);
        assertEquals("06105023389842834748547303", account1.getAccount());
        assertEquals(0, account1.getDebitCount());
        assertEquals(1, account1.getCreditCount());
        assertEquals(BigDecimal.valueOf(10.9).setScale(2), account1.getBalance());

        Account account2 = result.get(1);
        assertEquals("31074318698137062235845814", account2.getAccount());
        assertEquals(1, account2.getDebitCount());
        assertEquals(0, account2.getCreditCount());
        assertEquals(BigDecimal.valueOf(-200.9).setScale(2), account2.getBalance());

        Account account3 = result.get(2);
        assertEquals("32309111922661937852684864", account3.getAccount());
        assertEquals(1, account3.getDebitCount());
        assertEquals(1, account3.getCreditCount());
        assertEquals(BigDecimal.valueOf(39.20).setScale(2), account3.getBalance());

        Account account4 = result.get(3);
        assertEquals("66105036543749403346524547", account4.getAccount());
        assertEquals(1, account4.getDebitCount());
        assertEquals(1, account4.getCreditCount());
        assertEquals(BigDecimal.valueOf(150.80).setScale(2), account4.getBalance());
    }

}
