package manager.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasksIdList;

    public Epic(String title, String description) {
        super(title, description, TaskStatus.NEW);
        this.subTasksIdList = new ArrayList<>();
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
