package tasks;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected TaskStatus status;
    protected String description;
    protected TaskType type;
    protected LocalDateTime startTime;
    protected int duration;

    public Task (String name, String description, LocalDateTime startTime, int duration) {
        this.name = name;
        this.status = TaskStatus.NEW;
        this.description = description;
        this.type = TaskType.TASK;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task (String name, String description) {
        this.name = name;
        this.status = TaskStatus.NEW;
        this.description = description;
        this.type = TaskType.TASK;

    }

    public Task (int id, String name, String description, LocalDateTime startTime, int duration) {
        this.id = id;
        this.name = name;
        this.status = TaskStatus.NEW;
        this.description = description;
        this.type = TaskType.TASK;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task (int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.status = TaskStatus.NEW;
        this.description = description;
        this.type = TaskType.TASK;
        this.startTime = null;
        this.duration = 0;
    }

    public Task (int id, String name, TaskStatus status, String description, LocalDateTime startTime, int duration) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.type = TaskType.TASK;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task (int id, String name, TaskStatus status, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.type = TaskType.TASK;
        this.startTime = null;
        this.duration = 0;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && name.equals(task.name) && status.equals(task.status) && description.equals(task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", startTime=" + startTime + '\'' +
                ", duration=" + duration +
                '}';
    }


    public TaskType getType() {
        return type;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public int getDuration(){
        return duration;
    }



}
