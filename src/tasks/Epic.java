package tasks;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {



    protected ArrayList<Integer> subtaskIds = new ArrayList<>();

    protected LocalDateTime endTime;
    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    public Epic(String name, String description) {
        super(name, description,LocalDateTime.now(), 0);
        this.type = TaskType.EPIC;
    }

    public Epic(int id, String name, String description) {
        super(id, name, description, null, 0);
        this.status = TaskStatus.NEW;
        this.type = TaskType.EPIC;
    }

    public Epic(int id, String name,TaskStatus status, String description, LocalDateTime time, int duration, LocalDateTime endTime) {
        super(id, name, status, description, time, duration);
        this.type = TaskType.EPIC;

    }


    public ArrayList<Integer> getSubtaskId(){
        return subtaskIds;
    }

    public void cleanSubtaskIds(){
        subtaskIds.clear();
    }

    public void removeSubtask(Integer id){
        subtaskIds.remove(id);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime(){
        return endTime;
    }


    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", subtaskId=" + subtaskIds +'\'' +
                ", startTime=" + startTime + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds) && endTime.isEqual(epic.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds, endTime);
    }
}
