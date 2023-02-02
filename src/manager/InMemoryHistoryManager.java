package manager;
import tasks.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
  private final Map<Integer, Node> nodeMap = new HashMap<>();
  private Node head;
  private Node tail;


    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node node = head;
        if (head == null){
            System.out.println("История пуста");
        } else {
            while (node.getNext() != null) {
                history.add(node.getTask());
                node = node.getNext();
            }
            history.add(tail.getTask());
        }
       return history;
    }

    @Override
    public void addTask(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        Node newTask = new Node(task);
        linkLast(newTask);
        nodeMap.put(task.getId(), newTask);

    }

    private void linkLast(Node node){

        if (tail == null){
           head = node;
            tail = node;
        } else if (tail == head){
            node.setPrev(head);
            head.setNext(node);
            tail = node;
        } else {
            node.setPrev(tail);
            tail.setNext(node);
            tail = node;
        }
    }

    @Override
    public void remove(int id){
        Node removedNode = nodeMap.get(id);
        nodeMap.remove(id);
        if (head == tail){
            System.out.println("История пуста");
            head = null;
            tail = null;
            removedNode.setTask(null);
        } else {
            if (removedNode == head){
                head = removedNode.getNext();
            } else if(removedNode == tail){
                tail = removedNode.getPrev();
                removedNode.getPrev().setNext(null);
            }
            removeNode(removedNode);
        }
    }
    public void removeNode (Node node){
        if (node.getPrev() == null){
            node.getNext().setPrev(null);
        } else if(node.getNext() == null){
            node.getPrev().setNext(null);
        } else{
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }
    }

}
