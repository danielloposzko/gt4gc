package pl.danlop.greencode.onlinegame.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import pl.danlop.greencode.onlinegame.model.Clan;
import pl.danlop.greencode.onlinegame.model.Players;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author daniel
 */
class OnlineGameServiceTest {

    private OnlineGameService onlineGameService;

    @BeforeEach
    public void setUp() {
        onlineGameService = new OnlineGameService();
    }

    @Test
    void testCalculate_noClans_returnsEmptyList() {
        Players players = new Players(0, Arrays.asList());

        List<List<Clan>> result = onlineGameService.calculate(players);

        assertEquals(0, result.size());
    }

    @Test
    void testCalculate_singleClanFitsInGroup_returnsOneGroupWithSingleClan() {
        Players players = new Players(3, Arrays.asList(
                new Clan(2, 2)
        ));

        List<List<Clan>> result = onlineGameService.calculate(players);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).size());
        assertEquals(new Clan(2, 2), result.get(0).get(0));
    }

    @Test
    void testCalculate_singleClanTooLargeForGroup_returnsEmptyList() {
        Players players = new Players(3, Arrays.asList(
                new Clan(10, 4)
        ));

        List<List<Clan>> result = onlineGameService.calculate(players);

        assertEquals(0, result.size());
    }

    @Test
    void testCalculate_multipleClansFitsInGroup_returnsOneGroupWithMultipleClans() {
        Players players = new Players(4, Arrays.asList(
                new Clan(2, 1),
                new Clan(2, 2)
        ));

        List<List<Clan>> result = onlineGameService.calculate(players);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).size());
        assertEquals(new Clan(2, 2), result.get(0).get(0));
        assertEquals(new Clan(2, 1), result.get(0).get(1));
    }

    @Test
    void testCalculate_multipleClansSomeFitInGroup_returnsOneGroupWithSomeClans() {
        Players players = new Players(10, Arrays.asList(
                new Clan(15, 1),
                new Clan(5, 2),
                new Clan(4, 3)
        ));

        List<List<Clan>> result = onlineGameService.calculate(players);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).size());
        assertEquals(new Clan(4, 3), result.get(0).get(0));
        assertEquals(new Clan(5, 2), result.get(0).get(1));
    }

    @Test
    void testCalculate_multipleClansFitInMultipleGroups_returnsMultipleGroupsWithClans() {
        Players players = new Players(5, Arrays.asList(
                new Clan(3, 20),
                new Clan(4, 10),
                new Clan(2, 10),
                new Clan(1, 5)
        ));

        List<List<Clan>> result = onlineGameService.calculate(players);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).size());
        assertEquals(new Clan(3, 20), result.get(0).get(0));
        assertEquals(new Clan(2, 10), result.get(0).get(1));
        assertEquals(2, result.get(1).size());
        assertEquals(new Clan(4, 10), result.get(1).get(0));
        assertEquals(new Clan(1, 5), result.get(1).get(1));
    }

    @Test
    void testCalculate_exampleRequest_returnsExampleResponse() {
        Players players = new Players(6, Arrays.asList(
                new Clan(4, 50),
                new Clan(2, 70),
                new Clan(6, 60),
                new Clan(1, 15),
                new Clan(5, 40),
                new Clan(3, 45),
                new Clan(1, 12),
                new Clan(4, 40)
        ));

        List<List<Clan>> result = onlineGameService.calculate(players);

        assertEquals(5, result.size());
        assertEquals(2, result.get(0).size());
        assertEquals(new Clan(2, 70), result.get(0).get(0));
        assertEquals(new Clan(4, 50), result.get(0).get(1));
        assertEquals(1, result.get(1).size());
        assertEquals(new Clan(6, 60), result.get(1).get(0));
        assertEquals(3, result.get(2).size());
        assertEquals(new Clan(3, 45), result.get(2).get(0));
        assertEquals(new Clan(1, 15), result.get(2).get(1));
        assertEquals(new Clan(1, 12), result.get(2).get(2));
        assertEquals(1, result.get(3).size());
        assertEquals(new Clan(4, 40), result.get(3).get(0));
        assertEquals(1, result.get(4).size());
        assertEquals(new Clan(5, 40), result.get(4).get(0));
    }

}
