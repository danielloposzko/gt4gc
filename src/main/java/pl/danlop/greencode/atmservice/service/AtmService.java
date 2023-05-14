package pl.danlop.greencode.atmservice.service;

import pl.danlop.greencode.atmservice.model.ATM;
import pl.danlop.greencode.atmservice.model.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Service class that provides ATM related operations.
 *
 * This class is responsible for sorting and organizing ATMs based on the list of tasks.
 * The ATMs are sorted by region and request type.
 *
 * @author daniel
 */
public class AtmService {

    private static final Comparator<Task> TASK_COMPARATOR = Comparator.comparing(Task::getRegion)
            .thenComparing(Task::getRequestType);

    /**
     * This method sorts the list of tasks by region and request type using the TASK_COMPARATOR.
     * Then, it maps each task to a new ATM object with the same region and ATM ID,
     * ensuring each ATM is distinct. Finally, it collects the ATMs into a list.
     *
     * @param tasks The list of tasks to be sorted and converted into ATMs
     * @return A sorted and organized list of ATMs
     */
    public List<ATM> calculateOrder(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }

        tasks.sort(TASK_COMPARATOR);
        return tasks.stream()
                .map(task -> new ATM(task.getRegion(), task.getAtmId()))
                .distinct()
                .toList();
    }

}
