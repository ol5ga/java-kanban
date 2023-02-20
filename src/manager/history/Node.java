package manager.history;
import tasks.Task;

public class Node {
    private Task task;
    private Node next;
    private Node prev;


    public Node(Task task) {
        this.task = task;
        Node next = null;
        Node prev = null;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}

