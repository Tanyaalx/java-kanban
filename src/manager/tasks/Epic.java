package manager.tasks;
import manager.manager.TasksTypes;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasksIdList;
    private TasksTypes typeTask;

    public Epic(String title, String description) {
        super(title, description, TaskStatus.NEW);
        this.subTasksIdList = new ArrayList<>();
        this.typeTask = TasksTypes.EPIC_TYPES;
    }

    public ArrayList<Integer> getSubTasksIdList() {
        return subTasksIdList;
    }

    public void addSubtask(int subTaskId) {
        subTasksIdList.add(subTaskId);
    }

    public void deleteSubTask(int subtaskId) {
        subTasksIdList.remove((Integer) subtaskId);
    }

    public TasksTypes getType() {
        return typeTask;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                ", description='" + getDescription() + "'" +
                ", status=" + getStatus() +
                ", subTasksIdList=" + subTasksIdList +
                "}";
    }

}
