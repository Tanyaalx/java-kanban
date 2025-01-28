package manager.manager;

import java.util.ArrayList;
import java.util.HashMap;
import manager.tasks.*;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int counter = 0;

    public int createId() {
        return ++counter;
    }

    public void addTask(Task task) {
        int id = createId();
        task.setId(id);
        tasks.put(id, task);
    }

    public void deleteTaskById(int taskId) {

        tasks.remove(taskId);
    }

    public Task getTask(int taskId) {

        return tasks.get(taskId);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteAllTasks() { // удаление всех задач
        tasks.clear();
    }




    public void addEpic(Epic epic) {
        int id = createId();
        epic.setId(id);
        epics.put(id, epic);
    }

    public void deleteEpicById(int epicId) {

        Epic epic = epics.remove(epicId);

        if (epic != null) {
            for (int idSubTasks : epic.getSubTasksIdList()) {
                subTasks.remove(idSubTasks);
            }
        }
    }

    public Epic getEpic(int epicId) {
        return epics.get(epicId);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
    }



    public void addSubTask(SubTask subTask) {

        int id = createId();
        subTask.setId(id);
        subTasks.put(id, subTask);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            epic.addSubtask(id);
            updateEpicStatus(epic);
        }
    }

    public void deleteSubTaskById(int subTaskId) {

        SubTask subTask = subTasks.remove(subTaskId);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.deleteSubTask(subTaskId);
                updateEpicStatus(epic);
            }
        }
    }

    public SubTask getSubTaskById(int id) {

        return subTasks.get(id);
    }

    public ArrayList<SubTask> getSubtasksForEpic(int epicId) {
        ArrayList<SubTask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int subTaskId : epic.getSubTasksIdList()) {
                result.add(subTasks.get(subTaskId));
            }
        }
        return result;
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<Integer> subTasksIdList = epic.getSubTasksIdList();
        if (subTasksIdList.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allDone = true;
        boolean inProgress = false;

        for (int subTaskId : subTasksIdList) {
            TaskStatus status = subTasks.get(subTaskId).getStatus();
            if (status != TaskStatus.DONE) {
                allDone = false;
            }
            if (status == TaskStatus.IN_PROGRESS) {
                inProgress = true;
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (inProgress) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }
}