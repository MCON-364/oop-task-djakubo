package edu.touro.las.mcon364.taskmanager;

public class TaskManager {

    private final TaskRegistry registry;

    public TaskManager(TaskRegistry registry) {
        this.registry = registry;
    }

    // Current implementation uses old-style instanceof checks
    public void run(Command command) {
        switch (command) {
            case AddTaskCommand add -> add.execute();
            case RemoveTaskCommand remove -> remove.execute();
            case UpdateTaskCommand update -> update.execute();
            case UpdateTaskPriority updatePriority -> updatePriority.execute();
        }

    }
}
