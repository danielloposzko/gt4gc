package pl.danlop.greencode.transactions.controller;

import pl.danlop.greencode.transactions.model.Account;
import pl.danlop.greencode.transactions.model.Transaction;
import pl.danlop.greencode.transactions.service.TransactionsService;
import pl.danlop.greencode.util.server.HttpPostRequestHandler;
import pl.danlop.greencode.util.mapper.DataToObjectMapper;

import java.util.List;

/**
 * Controller class for managing the endpoints related to transactions.
 *
 * This class extends the HttpPostRequestHandler and is responsible for handling HTTP POST requests
 * related to the transactions service. The main function of this class is to pass the Transaction data
 * received from the HTTP request to the transactions service, and return the result.
 *
 * @author daniel
 */
public class TransactionsController extends HttpPostRequestHandler<List<Transaction>, List<Account>> {

    private final TransactionsService transactionsService;

    /**
     * Creates a new TransactionsController.
     *
     * @param uri the URI for this controller
     * @param mapper the DataToObjectMapper used to map incoming data to Java objects
     * @param transactionsService the TransactionsService instance used to process logic
     */
    public TransactionsController(String uri, DataToObjectMapper<List<Transaction>, List<Account>> mapper,
                                  TransactionsService transactionsService) {
        super(uri, mapper);
        this.transactionsService = transactionsService;
    }

    /**
     * This method overrides the abstract method from HttpPostRequestHandler.
     * It passes the list of Transaction objects to the transactions service to generate a report
     * and return the result as a list of Account objects updated with transaction activity.
     *
     * @param transactions The list of Transaction objects to report on
     * @return A sorted list of Account objects updated with transaction activity
     */
    @Override
    public List<Account> doLogic(List<Transaction> transactions) {
        return transactionsService.report(transactions);
    }

}
