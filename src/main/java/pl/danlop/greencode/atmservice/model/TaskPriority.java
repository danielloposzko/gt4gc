package pl.danlop.greencode.atmservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TaskPriority {

    FAILURE_RESTART(0),
    PRIORITY(1),
    SIGNAL_LOW(2),
    STANDARD(3);

    private final int value;

}
