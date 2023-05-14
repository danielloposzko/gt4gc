package pl.danlop.greencode.util.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import pl.danlop.greencode.util.mapper.DataToObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An abstract HTTP POST request handler that performs the basic logic of processing
 * HTTP POST requests and leaves the actual business logic to subclasses.
 *
 * <p>This class uses a {@code DataToObjectMapper} to deserialize the HTTP request body
 * into an object of type {@code REQ} and serialize the response of type {@code RES} back
 * to the client.
 *
 * <p>The HTTP response codes used are 200 for successful processing, 405 for wrong request
 * method, and 400 for all kinds of bad request errors.
 *
 * @param <REQ> The type of the request object.
 * @param <RES> The type of the response object.
 *
 * @author daniel
 */
public abstract class HttpPostRequestHandler<REQ, RES> implements HttpHandler {

    private static final String POST_METHOD = "POST";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_BAD_REQUEST = 400;
    private static final int HTTP_STATUS_METHOD_NOT_ALLOWED = 405;

    private final String uri;
    private final DataToObjectMapper<REQ, RES> mapper;

    /**
     * Constructs a new {@code HttpPostRequestHandler} with the provided URI and mapper.
     *
     * @param uri The URI that this handler will be bound to.
     * @param mapper The {@code DataToObjectMapper} to use for request deserialization and response serialization.
     */
    protected HttpPostRequestHandler(String uri, DataToObjectMapper<REQ, RES> mapper) {
        this.uri = uri;
        this.mapper = mapper;
    }

    /**
     * Executes the business logic on the deserialized request object.
     *
     * @param request The request object.
     * @return The response object.
     */
    public abstract RES doLogic(REQ request);

    /**
     * Handles the HTTP exchange, deserializing the request, calling {@code doLogic} to process it,
     * and sending the serialized response back to the client.
     *
     * @param exchange The HTTP exchange.
     * @throws IOException If an I/O error occurs during the handling of the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!POST_METHOD.equals(exchange.getRequestMethod())) {
            response(exchange, HTTP_STATUS_METHOD_NOT_ALLOWED, "Wrong request method.");
            return;
        }
        try {
            REQ input = mapper.deserialize(exchange.getRequestBody());
            RES result = doLogic(input);
            String output = mapper.serialize(result);
            response(exchange, HTTP_STATUS_OK, output);
        } catch (Exception e) {
            response(exchange, HTTP_STATUS_BAD_REQUEST, e.getMessage());
        }
    }

    private void response(HttpExchange exchange, int code, String result) throws IOException {
        exchange.getResponseHeaders().add(CONTENT_TYPE, APPLICATION_JSON);
        exchange.sendResponseHeaders(code, result.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(result.getBytes());
        os.close();
    }

    /**
     * Returns the URI that this handler is bound to.
     *
     * @return The URI.
     */
    public String getUri() {
        return uri;
    }

}
