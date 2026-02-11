package edu.touro.las.mcon364.taskmanager;

import java.util.Optional;

public final class UpdateTaskCommand implements Command {
    private final TaskRegistry registry;
    private final String taskName;
    private final Priority newPriority;

    public UpdateTaskCommand(TaskRegistry registry, String taskName, Priority newPriority) {
        this.registry = registry;
        this.taskName = taskName;
        this.newPriority = newPriority;
    }

    public void execute() {
        // NOTE: This demonstrates old-style null checking
        // Students should refactor to use Optional and custom exceptions
        var existing = Optional.ofNullable(registry.get(taskName))
                .orElseThrow(() -> new TaskNotFoundException
                        ("Task '" + taskName + "' not found"));

        // Create a new task with updated priority (tasks are immutable)
        Task updated = new Task(existing.get().name(), newPriority);
        registry.add(updated);  // This replaces the old task
    }
}
