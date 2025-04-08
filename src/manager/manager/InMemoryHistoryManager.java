package manager.manager;
import manager.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node<Task>> mapHistory = new HashMap<>();
    private Node<Task> tail;
    private Node<Task> head;

    private void linkLast(Task task) {
        Node<Task> newNode = new Node<>(task);
        if (head == null) {
            tail = newNode;
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        mapHistory.put(task.getId(), newNode);
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> nodeCurrent = head;
        while (nodeCurrent != null) {
            tasks.add(nodeCurrent.data);
            nodeCurrent = nodeCurrent.next;
        }
        return tasks;
    }

    @Override
    public void add(Task task) {
        remove(task.getId());
        linkLast(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        Node<Task> node = mapHistory.remove(id);
        if (node == null) {
            return;
        }
        removeNode(node);
    }

    private void removeNode(Node<Task> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }
}
