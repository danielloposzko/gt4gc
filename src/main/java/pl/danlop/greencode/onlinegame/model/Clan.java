package pl.danlop.greencode.onlinegame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clan {

    private int numberOfPlayers;
    private int points;

}
