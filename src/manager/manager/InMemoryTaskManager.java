package manager.manager;

import java.util.ArrayList;
import java.util.HashMap;

import manager.tasks.*;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private int counter = 0;

    public int createId() {
        return ++counter;
    }

    @Override
    public Task addTask(Task task) {
        int id = createId();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @Override
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public Task getTask(int taskId) {
        historyManager.addHistory(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        int id = createId();
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
    public void deleteEpicById(int epicId) {
        Epic epic = epics.remove(epicId);
        if (epic != null) {
            for (int idSubTasks : epic.getSubTasksIdList()) {
                subTasks.remove(idSubTasks);
            }
        }
    }

    @Override
    public Epic getEpic(int epicId) {
        historyManager.addHistory(epics.get(epicId));
        return epics.get(epicId);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void updateEpics(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
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

    @Override
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

    @Override
    public SubTask getSubTaskById(int id) {
        historyManager.addHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
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

    @Override
    public void updateEpicStatus(Epic epic) {
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
                break;
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

    @Override
    public void updateSubTasks(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);

            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

}