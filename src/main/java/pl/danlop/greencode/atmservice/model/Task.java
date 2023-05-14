package pl.danlop.greencode.atmservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private int region;
    private TaskPriority requestType;
    private int atmId;

}
