public class Todo extends Task {
    public Todo(String description, TaskType taskType) {
        super(description);
        this.taskType = taskType;
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}