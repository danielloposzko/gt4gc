package pl.danlop.greencode.onlinegame.controller;

import pl.danlop.greencode.onlinegame.model.Clan;
import pl.danlop.greencode.onlinegame.model.Players;
import pl.danlop.greencode.onlinegame.service.OnlineGameService;
import pl.danlop.greencode.util.server.HttpPostRequestHandler;
import pl.danlop.greencode.util.mapper.DataToObjectMapper;

import java.util.List;

/**
 * Controller class for managing the endpoints related to the online game.
 *
 * This class extends the HttpPostRequestHandler and is responsible for handling HTTP POST requests
 * related to the online game service. The main function of this class is to pass the Players data
 * received from the HTTP request to the online game service, and return the result.
 *
 * @author daniel
 */
public class OnlineGameController extends HttpPostRequestHandler<Players, List<List<Clan>>> {

    private final OnlineGameService onlineGameService;

    /**
     * Creates a new OnlineGameController.
     *
     * @param uri the URI for this controller
     * @param mapper the DataToObjectMapper used to map incoming data to Java objects
     * @param onlineGameService the OnlineGameService instance used to process logic
     */
    public OnlineGameController(String uri, DataToObjectMapper<Players, List<List<Clan>>> mapper,
                                OnlineGameService onlineGameService) {
        super(uri, mapper);
        this.onlineGameService = onlineGameService;
    }

    /**
     * This method overrides the abstract method from HttpPostRequestHandler.
     * It passes the Players object to the online game service to calculate and return
     * the result as a list of clans grouped according to the players' data.
     *
     * @param players The Players object containing the clans and group count
     * @return A list of clans grouped according to the players' data
     */
    @Override
    public List<List<Clan>> doLogic(Players players) {
        return onlineGameService.calculate(players);
    }

}
