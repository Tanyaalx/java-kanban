package manager.manager;

import manager.tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> historyList = new ArrayList<>();
    private static final int HISTORYLIST_LIMIT = 10;

@Override
    public void addHistory(Task task){
        if(historyList.size() == HISTORYLIST_LIMIT) {
            historyList.removeFirst();
            historyList.add(task);

        } else {
            historyList.add(task);
        }
    }


@Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(historyList);
    }
}
