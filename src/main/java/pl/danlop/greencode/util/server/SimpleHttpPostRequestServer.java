package pl.danlop.greencode.util.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 * A simple HTTP server class that handles (only) POST requests.
 *
 * <p>This class wraps the {@code HttpServer} class provided by the
 * com.sun.net.httpserver package, and provides an easy way to create
 * a multithreaded HTTP server that can handle multiple HTTP POST request
 * handlers.
 *
 * <p>The server is started by calling the {@code start} method.
 *
 * @author daniel
 */
public class SimpleHttpPostRequestServer {

    private final HttpServer httpServer;

    /**
     * Constructs a new {@code SimpleHttpPostRequestServer} with the provided port,
     * number of threads, and handlers.
     *
     * <p>The server is not started immediately; you need to call the {@code start} method to start it.
     *
     * @param port The port to bind the server to.
     * @param threads The number of threads to use for handling HTTP requests.
     * @param handlers The HTTP POST request handlers.
     * @throws IOException If an I/O error occurs when creating the server.
     */
    public SimpleHttpPostRequestServer(int port, int threads, HttpPostRequestHandler<?, ?>... handlers) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        this.httpServer.setExecutor(Executors.newFixedThreadPool(threads));
        Arrays.stream(handlers)
                .forEach(h -> this.httpServer.createContext(h.getUri(), h));
    }

    /**
     * Starts the server.
     *
     * <p>Once the server is started, it begins listening for incoming HTTP connections
     * on the configured port.
     */
    public void start() {
        this.httpServer.start();
    }

}
