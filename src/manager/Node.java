package manager;
import tasks.Task;

public class Node {
    Task task;
    Node next;
    Node prev;

    /*removeNode(Node node){
        if (node.prev == null){
            node.next.prev = null;
        } else if(node.next == next){
            tail = removedNode.prev;
            removedNode.prev.next = null;
        } else{
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
        }
    }*/
}

