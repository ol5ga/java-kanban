package manager;
import tasks.Task;

public class Node {
    Task task;
    Node next;
    Node prev;

    public void removeNode (Node node){
        if (node.prev == null){
            node.next.prev = null;
        } else if(node.next == null){
            node.prev.next = null;
        } else{
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}

