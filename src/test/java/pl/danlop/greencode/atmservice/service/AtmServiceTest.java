package pl.danlop.greencode.atmservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import pl.danlop.greencode.atmservice.model.ATM;
import pl.danlop.greencode.atmservice.model.Task;
import pl.danlop.greencode.atmservice.model.TaskPriority;

import java.util.Arrays;
import java.util.List;

/**
 * @author daniel
 */
class AtmServiceTest {

    private AtmService atmService;

    @BeforeEach
    public void setUp() {
        atmService = new AtmService();
    }

    @Test
    void testCalculateOrder_noTasks_returnsEmptyList() {
        List<Task> tasks = Arrays.asList();

        List<ATM> result = atmService.calculateOrder(tasks);

        assertEquals(0, result.size());
    }

    @Test
    void testCalculateOrder_singleTask_returnsSingleAtm() {
        List<Task> tasks = Arrays.asList(
                new Task(1, TaskPriority.STANDARD, 1)
        );

        List<ATM> result = atmService.calculateOrder(tasks);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getRegion());
        assertEquals(1, result.get(0).getAtmId());
    }

    @Test
    void testCalculateOrder_multipleTasksDifferentRegions_returnsSortedAtms() {
        List<Task> tasks = Arrays.asList(
                new Task(3, TaskPriority.FAILURE_RESTART, 3),
                new Task(2, TaskPriority.STANDARD, 2),
                new Task(1, TaskPriority.PRIORITY, 1)
        );

        List<ATM> result = atmService.calculateOrder(tasks);

        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getRegion());
        assertEquals(2, result.get(1).getRegion());
        assertEquals(3, result.get(2).getRegion());
    }

    @Test
    void testCalculateOrder_multipleTasksSameRegionDifferentRequestTypes_returnsSortedAtms() {
        List<Task> tasks = Arrays.asList(
                new Task(1, TaskPriority.FAILURE_RESTART, 3),
                new Task(1, TaskPriority.PRIORITY, 1),
                new Task(1, TaskPriority.STANDARD, 2)
        );

        List<ATM> result = atmService.calculateOrder(tasks);

        assertEquals(3, result.size());
        assertEquals(3, result.get(0).getAtmId());
        assertEquals(1, result.get(1).getAtmId());
        assertEquals(2, result.get(2).getAtmId());
    }

    @Test
    void testCalculateOrder_multipleTasksSameRegionAndDiffRequestTypes_returnsDistinctAtms() {
        List<Task> tasks = Arrays.asList(
                new Task(1, TaskPriority.PRIORITY, 1),
                new Task(1, TaskPriority.STANDARD, 1),
                new Task(1, TaskPriority.FAILURE_RESTART, 1)
        );

        List<ATM> result = atmService.calculateOrder(tasks);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getAtmId());
    }

    @Test
    void testCalculateOrder_multipleTasksMixedRegionsAndRequestTypes_returnsSortedDistinctAtms() {
        List<Task> tasks = Arrays.asList(
                new Task(1, TaskPriority.PRIORITY, 1),
                new Task(1, TaskPriority.STANDARD, 2),
                new Task(1, TaskPriority.FAILURE_RESTART, 2),
                new Task(2, TaskPriority.FAILURE_RESTART, 3),
                new Task(2, TaskPriority.STANDARD, 3)
        );

        List<ATM> result = atmService.calculateOrder(tasks);

        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getAtmId());
        assertEquals(1, result.get(1).getAtmId());
        assertEquals(3, result.get(2).getAtmId());
    }

    @Test
    void testCalculateOrder_example1Request_returnsExample1Response() {
        List<Task> tasks = Arrays.asList(
                new Task(4, TaskPriority.STANDARD, 1),
                new Task(1, TaskPriority.STANDARD, 1),
                new Task(2, TaskPriority.STANDARD, 1),
                new Task(3, TaskPriority.PRIORITY, 2),
                new Task(3, TaskPriority.STANDARD, 1),
                new Task(2, TaskPriority.SIGNAL_LOW, 1),
                new Task(5, TaskPriority.STANDARD, 2),
                new Task(5, TaskPriority.FAILURE_RESTART, 1)
        );

        List<ATM> result = atmService.calculateOrder(tasks);

        assertEquals(7, result.size());
        assertEquals(new ATM(1, 1), result.get(0));
        assertEquals(new ATM(2, 1), result.get(1));
        assertEquals(new ATM(3, 2), result.get(2));
        assertEquals(new ATM(3, 1), result.get(3));
        assertEquals(new ATM(4, 1), result.get(4));
        assertEquals(new ATM(5, 1), result.get(5));
        assertEquals(new ATM(5, 2), result.get(6));
    }

    @Test
    void testCalculateOrder_example2Request_returnsExample2Response() {
        List<Task> tasks = Arrays.asList(
                new Task(1, TaskPriority.STANDARD, 2),
                new Task(1, TaskPriority.STANDARD, 1),
                new Task(2, TaskPriority.PRIORITY, 3),
                new Task(3, TaskPriority.STANDARD, 4),
                new Task(4, TaskPriority.STANDARD, 5),
                new Task(5, TaskPriority.PRIORITY, 2),
                new Task(5, TaskPriority.STANDARD, 1),
                new Task(3, TaskPriority.SIGNAL_LOW, 2),
                new Task(2, TaskPriority.SIGNAL_LOW, 1),
                new Task(3, TaskPriority.FAILURE_RESTART, 1)
        );

        List<ATM> result = atmService.calculateOrder(tasks);

        assertEquals(10, result.size());
        assertEquals(new ATM(1, 2), result.get(0));
        assertEquals(new ATM(1, 1), result.get(1));
        assertEquals(new ATM(2, 3), result.get(2));
        assertEquals(new ATM(2, 1), result.get(3));
        assertEquals(new ATM(3, 1), result.get(4));
        assertEquals(new ATM(3, 2), result.get(5));
        assertEquals(new ATM(3, 4), result.get(6));
        assertEquals(new ATM(4, 5), result.get(7));
        assertEquals(new ATM(5, 2), result.get(8));
        assertEquals(new ATM(5, 1), result.get(9));
    }

}
