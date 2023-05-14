package pl.danlop.greencode.transactions.service;

import pl.danlop.greencode.transactions.model.Account;
import pl.danlop.greencode.transactions.model.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

/**
 * This service handles transaction processing and account reporting.
 *
 * It is responsible for generating a report that summarizes account activity based on a list of transactions.
 *
 * @author daniel
 */
public class TransactionsService {

    private static final Comparator<Account> ACCOUNT_COMPARATOR = Comparator.comparing(Account::getAccount);

    private static final BigDecimal ZERO = new BigDecimal(BigInteger.ZERO).setScale(2, RoundingMode.HALF_EVEN);

    /**
     * This method generates a report of account activity based on a list of transactions.
     * For each transaction, it updates the balance and transaction count of the debit and credit accounts.
     * The result is a sorted list of all accounts involved in the transactions.
     *
     * @param transactions A list of transactions to report on
     * @return A sorted list of accounts updated with transaction activity
     */
    public List<Account> report(List<Transaction> transactions ) {
        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Account> accountsMap = new HashMap<>();

        for (Transaction transaction : transactions) {
            updateDebitAccount(accountsMap, transaction);
            updateCreditAccount(accountsMap, transaction);
        }

        List<Account> accounts = new ArrayList<>(accountsMap.values());
        accounts.sort(ACCOUNT_COMPARATOR);

        return accounts;
    }

    private void updateDebitAccount(Map<String, Account> accountsMap, Transaction transaction) {
        Account debitAccount = accountsMap.getOrDefault(transaction.getDebitAccount(),
                new Account(transaction.getDebitAccount(), 0, 0, ZERO));
        debitAccount.setDebitCount(debitAccount.getDebitCount() + 1);
        debitAccount.setBalance(debitAccount.getBalance()
                .subtract(transaction.getAmount().setScale(2, RoundingMode.HALF_EVEN)));
        accountsMap.put(transaction.getDebitAccount(), debitAccount);
    }

    private void updateCreditAccount(Map<String, Account> accountsMap, Transaction transaction) {
        Account creditAccount = accountsMap.getOrDefault(transaction.getCreditAccount(),
                new Account(transaction.getCreditAccount(), 0, 0, ZERO));
        creditAccount.setCreditCount(creditAccount.getCreditCount() + 1);
        creditAccount.setBalance(creditAccount.getBalance()
                .add(transaction.getAmount().setScale(2, RoundingMode.HALF_EVEN)));
        accountsMap.put(transaction.getCreditAccount(), creditAccount);
    }

}
