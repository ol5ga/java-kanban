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

    public Epic(String name, String description, LocalDateTime startTime, int duration) {
        super(name, description, startTime, duration);
        this.type = TaskType.EPIC;

    }

    public Epic(int id, String name, String description, LocalDateTime startTime, int duration) {
        super(id, name, description, startTime, duration);
        this.status = TaskStatus.NEW;
        this.type = TaskType.EPIC;
        this.startTime = LocalDateTime.now();
        this.duration = 0;

    }
    public Epic(int id, String name, TaskStatus status, String description, LocalDateTime startTime, int duration) {
        super(id, name, status, description, startTime, duration);
        this.status = TaskStatus.NEW;
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
    public void setDuration(int duration) {
        this.duration = duration;
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
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }
}
