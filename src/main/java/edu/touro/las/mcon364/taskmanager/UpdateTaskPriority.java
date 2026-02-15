package edu.touro.las.mcon364.taskmanager;

public final class UpdateTaskPriority implements Command {
    private final TaskRegistry registry;
    private final String name;
    private final Priority newPriority;

    public UpdateTaskPriority(TaskRegistry registry, String name, Priority newPriority) {
        if(newPriority == null){
            throw new InvalidPriorityException(newPriority);
        }
        this.registry = registry;
        this.name = name;
        this.newPriority = newPriority; 
    }

    public void execute() {
        Task oldTask = registry.get(name)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        Task updatedTask = new Task(oldTask.name(), newPriority);
        registry.remove(oldTask.name());
        registry.add(updatedTask);

    }


}
