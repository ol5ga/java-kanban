package manager;
import tasks.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
  public Map<Integer, Node> nodeMap = new HashMap<>();
  private Node head;
  private Node tail;
  public Node node = new Node();

    @Override
    public List<Task> getHistory() {
       List<Task> history = new ArrayList<>();
       Node node = head;
       while(node.next != null){
           history.add(node.task);
           node = node.next;
       }
       history.add(tail.task);
       return history;
    }

    @Override
    public void addTask(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        Node newTask = new Node();

        if (head == null){
            newTask.task = task;
            head = newTask;
        } else if (tail == null){
            newTask.task = task;
            newTask.prev = head;
            head.next = newTask;
            tail = newTask;
        } else {
            newTask.task = task;
            newTask.prev = tail;
            tail.next = newTask;
            tail = newTask;
        }
        nodeMap.put(newTask.task.getId(), newTask);

    }

    @Override
    public void remove(int id){
        Node removedNode = nodeMap.get(id);
        nodeMap.remove(id);
        if (removedNode == head){
            head = removedNode.next;
        } else if(removedNode == tail){
            tail = removedNode.prev;
            removedNode.prev.next = null;
        }
        node.removeNode(removedNode);

    }

}
