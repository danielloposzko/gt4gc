package pl.danlop.greencode.atmservice.controller;

import pl.danlop.greencode.atmservice.model.ATM;
import pl.danlop.greencode.atmservice.model.Task;
import pl.danlop.greencode.atmservice.service.AtmService;
import pl.danlop.greencode.util.server.HttpPostRequestHandler;
import pl.danlop.greencode.util.mapper.DataToObjectMapper;

import java.util.List;

/**
 * Controller class for managing the endpoints related to ATM service.
 *
 * This class extends the HttpPostRequestHandler and is responsible for handling HTTP POST requests
 * related to the ATM service. The main function of this class is to pass the Task data
 * received from the HTTP request to the ATM service, and return the result.
 *
 * @author daniel
 */
public class AtmServiceController extends HttpPostRequestHandler<List<Task>, List<ATM>> {

    private final AtmService atmService;

    /**
     * Creates a new AtmServiceController.
     *
     * @param uri the URI for this controller
     * @param mapper the DataToObjectMapper used to map incoming data to Java objects
     * @param atmService the AtmService instance used to process logic
     */
    public AtmServiceController(String uri, DataToObjectMapper<List<Task>, List<ATM>> mapper,
                                AtmService atmService) {
        super(uri, mapper);
        this.atmService = atmService;
    }

    /**
     * This method overrides the abstract method from HttpPostRequestHandler.
     * It passes the list of Task objects to the ATM service to calculate the order of ATMs
     * and return the result as a list of ATM objects.
     *
     * @param tasks The list of Task objects to calculate order
     * @return A list of ATM objects in calculated order
     */
    @Override
    public List<ATM> doLogic(List<Task> tasks) {
        return atmService.calculateOrder(tasks);
    }

}
