package pl.danlop.greencode;

import com.fasterxml.jackson.core.type.TypeReference;
import pl.danlop.greencode.atmservice.controller.AtmServiceController;
import pl.danlop.greencode.atmservice.service.AtmService;
import pl.danlop.greencode.onlinegame.controller.OnlineGameController;
import pl.danlop.greencode.onlinegame.service.OnlineGameService;
import pl.danlop.greencode.transactions.controller.TransactionsController;
import pl.danlop.greencode.transactions.service.TransactionsService;
import pl.danlop.greencode.util.server.HttpPostRequestHandler;
import pl.danlop.greencode.util.mapper.JacksonDataToObjectMapperImpl;
import pl.danlop.greencode.util.server.SimpleHttpPostRequestServer;

import java.io.IOException;

/**
 * The main class for starting the HTTP server.
 *
 * <p>This class initializes a set of HTTP POST request handlers (endpoints)
 * and starts a {@code SimpleHttpPostRequestServer} with these handlers.
 *
 * <p>The server is multithreaded, and the number of threads can be configured
 * by modifying the {@code THREADS} constant. The server port can also be
 * configured by modifying the {@code PORT} constant.
 *
 * <p>The current set of endpoints includes:
 * <ul>
 *   <li>An ATM service controller that calculates ATM orders.
 *   <li>An onlinegame controller that calculates game results.
 *   <li>A transactions controller that generates transaction reports.
 * </ul>
 *
 * @author daniel
 */
public class Main {

    private static final int PORT = 8080;
    private static final int THREADS = 15;

    private static final String ATM_SERVICE_URI = "/atms/calculateOrder";
    private static final String ONLINE_GAME_URI = "/onlinegame/calculate";
    private static final String TRANSACTIONS_URI = "/transactions/report";

    private static final HttpPostRequestHandler<?, ?>[] ENDPOINTS = {
            new AtmServiceController(
                    ATM_SERVICE_URI,
                    new JacksonDataToObjectMapperImpl<>(new TypeReference<>() {}),
                    new AtmService()
            ),
            new OnlineGameController(
                    ONLINE_GAME_URI,
                    new JacksonDataToObjectMapperImpl<>(new TypeReference<>() {}),
                    new OnlineGameService()
            ),
            new TransactionsController(
                    TRANSACTIONS_URI,
                    new JacksonDataToObjectMapperImpl<>(new TypeReference<>() {}),
                    new TransactionsService()
            )
    };

    /**
     * The main entry point of the application.
     *
     * @param args The command-line arguments.
     * @throws IOException If an I/O error occurs when starting the server.
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Server has started!");
        new SimpleHttpPostRequestServer(PORT, THREADS, ENDPOINTS).start();
    }

}