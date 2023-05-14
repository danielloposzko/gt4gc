package pl.danlop.greencode.onlinegame.service;

import pl.danlop.greencode.onlinegame.model.Clan;
import pl.danlop.greencode.onlinegame.model.Players;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Service class that provides game related operations for the Online Game application.
 *
 * It is responsible for organizing and sorting clans into groups based on the points and the number
 * of players in each clan.
 *
 * @author daniel
 */
public class OnlineGameService {

    private static final Comparator<Clan> CLAN_COMPARATOR = Comparator.comparing(Clan::getPoints).reversed()
            .thenComparing(Clan::getNumberOfPlayers);

    /**
     * Calculate method organizes clans into groups based on the provided players.
     * The groups are sorted based on the clans' points and number of players.
     *
     * @param players Data model containing the clans and group count
     * @return A list of clans grouped according to the players' data
     */
    public List<List<Clan>> calculate(Players players) {
        if (players == null || players.getClans() == null || players.getClans().isEmpty()) {
            return Collections.emptyList();
        }

        int groupMax = players.getGroupCount();
        List<Clan> clans = players.getClans();

        clans.sort(CLAN_COMPARATOR);

        boolean[] taken = new boolean[clans.size()];
        List<List<Clan>> groups = new LinkedList<>();

        for (int i = 0; i < clans.size(); i++) {
            Clan currentClan = clans.get(i);
            if (taken[i] || currentClan.getNumberOfPlayers() > groupMax) {
                continue;
            }

            List<Clan> newGroup = new LinkedList<>();
            newGroup.add(currentClan);
            taken[i] = true;

            int currentGroupSize = currentClan.getNumberOfPlayers();
            if (currentGroupSize == groupMax) {
                groups.add(newGroup);
                continue;
            }

            for (int j = i + 1; j < clans.size(); j++) {
                if (taken[j]) {
                    continue;
                }

                currentClan = clans.get(j);
                if (currentGroupSize + currentClan.getNumberOfPlayers() <= groupMax) {
                    newGroup.add(currentClan);
                    taken[j] = true;
                    currentGroupSize += currentClan.getNumberOfPlayers();

                    if (currentGroupSize == groupMax) {
                        break;
                    }
                }
            }

            groups.add(newGroup);
        }

        return groups;
    }

}
