package pl.danlop.greencode.onlinegame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Players {

    private int groupCount;
    private List<Clan> clans;

}
