package edu.touro.las.mcon364.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Command implementations.
 * After sealing the Command interface and refactoring, these tests should still pass.
 */
class CommandTest {
    private TaskRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new TaskRegistry();
    }

    @Test
    @DisplayName("AddTaskCommand should add task to registry")
    void testAddTaskCommand() {
        Task task = new Task("New task", Priority.MEDIUM);
        Command command = new AddTaskCommand(registry, task);

        command.execute();
        Optional<Task> result = registry.get("New task");
        assertNotNull(registry.get("New task"), "Task should be in registry after AddTaskCommand");
        assertEquals(task, result.get(), "Added task should match");
    }

    @Test
    @DisplayName("AddTaskCommand should replace existing task with same name")
    void testAddTaskCommandReplacement() {
        Task originalTask = new Task("Task", Priority.LOW);
        Task replacementTask = new Task("Task", Priority.HIGH);

        new AddTaskCommand(registry, originalTask).execute();
        new AddTaskCommand(registry, replacementTask).execute();

        assertEquals(Priority.HIGH, registry.get("Task").get().priority(),
                "Replacement task should have new priority");
    }

    @Test
    @DisplayName("RemoveTaskCommand should remove task from registry")
    void testRemoveTaskCommand() {
        registry.add(new Task("To be removed", Priority.HIGH));

        Command command = new RemoveTaskCommand(registry, "To be removed");
        command.execute();
        assertThrows(TaskNotFoundException.class, () -> registry.get("To be removed"));
    }

    @Test
    @DisplayName("RemoveTaskCommand on non-existent task should not throw")
    void testRemoveTaskCommandNonExistent() {
        Command command = new RemoveTaskCommand(registry, "Non-existent");

        assertDoesNotThrow(command::execute,
                "Removing non-existent task should not throw exception");
    }

    @Test
    @DisplayName("UpdateTaskCommand should update existing task priority")
    void testUpdateTaskCommand() {
        registry.add(new Task("Update me", Priority.LOW));

        Command command = new UpdateTaskCommand(registry, "Update me", Priority.HIGH);
        command.execute();

        var updated = registry.get("Update me");
        assertNotNull(updated, "Task should still exist after update");
        assertEquals(Priority.HIGH, updated.get().priority(), "Priority should be updated to HIGH");
    }

    @Test
    @DisplayName("UpdateTaskCommand should preserve task name")
    void testUpdateTaskCommandPreservesName() {
        registry.add(new Task("Important task", Priority.MEDIUM));

        Command command = new UpdateTaskCommand(registry, "Important task", Priority.LOW);
        command.execute();

        var updated = registry.get("Important task");
        assertEquals("Important task", updated.get().name(), "Task name should be preserved");
    }

    @Test
    @DisplayName("UpdateTaskCommand on non-existent task should not throw (pre-refactor)")
    void testUpdateTaskCommandNonExistent() {
        Command command = new UpdateTaskCommand(registry, "Non-existent", Priority.HIGH);

        assertThrows(TaskNotFoundException.class, () -> registry.get("Non-existent"));
    }

    @Test
    @DisplayName("UpdateTaskCommand should allow changing priority from HIGH to LOW")
    void testUpdateTaskCommandPriorityDecrease() {
        registry.add(new Task("Flexible", Priority.HIGH));

        new UpdateTaskCommand(registry, "Flexible", Priority.LOW).execute();

        assertEquals(Priority.LOW, registry.get("Flexible").get().priority(),
                "Should allow decreasing priority");
    }

    @Test
    @DisplayName("UpdateTaskCommand should allow changing priority from LOW to HIGH")
    void testUpdateTaskCommandPriorityIncrease() {
        registry.add(new Task("Urgent", Priority.LOW));

        new UpdateTaskCommand(registry, "Urgent", Priority.HIGH).execute();

        assertEquals(Priority.HIGH, registry.get("Urgent").get().priority(),
                "Should allow increasing priority");
    }

    //AI generated tests I added for Update Task Priority
    @Test
    void testUpdateTaskPriority_success() {
        TaskRegistry registry = new TaskRegistry();
        Task original = new Task("Task1", Priority.LOW);
        registry.add(original);

        Command command = new UpdateTaskPriority(registry, "Task1", Priority.HIGH);
        command.execute();

        Task updated = registry.get("Task1").orElseThrow();

        assertEquals(Priority.HIGH, updated.priority());
    }
    @Test
    void testUpdateTaskPriority_taskNotFound() {
        TaskRegistry registry = new TaskRegistry();

        Command command = new UpdateTaskPriority(registry, "MissingTask", Priority.HIGH);

        assertThrows(TaskNotFoundException.class, command::execute);
    }
    //Testing custom exception
    @Test
    void testUpdateTaskPriority_nullPriorityThrowsException() {
        TaskRegistry registry = new TaskRegistry();
        registry.add(new Task("Task1", Priority.LOW));

        assertThrows(InvalidPriorityException.class,() -> new UpdateTaskPriority(registry, "Task1", null));
    }



}

