package manager.tasks;
import manager.manager.TasksTypes;

public class SubTask extends Task {
    private int epicId;
    private TasksTypes typeTask;

    public SubTask(String title, String description, int epicId, TaskStatus status) {
        super(title, description, status);
        this.epicId = epicId;
        this.typeTask = TasksTypes.SUBTASK_TYPES;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public TasksTypes getType() {
        return typeTask;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                ", description='" + getDescription() + "'" +
                ", status=" + getStatus() +
                ", epicID=" + epicId +
                "}";
    }
}
