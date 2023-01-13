package tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    protected ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        this.type = TaskType.EPIC;
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
        this.status = TaskStatus.NEW;
        this.type = TaskType.EPIC;
    }
    public Epic(int id, String name, TaskStatus status, String description) {
        super(id, name, status, description);
        this.status = TaskStatus.NEW;
        this.type = TaskType.EPIC;
    }


    public ArrayList<Integer> getSubtaskId(){
        return subtaskIds;
    }

    public void addSubtaskId(int id){
        subtaskIds.add(id);
    }

    public void cleanSubtaskIds(){
        subtaskIds.clear();
    }

    public void removeSubtask(Integer id){
        subtaskIds.remove(id);
    }




    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", subtaskId=" + subtaskIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }
}
