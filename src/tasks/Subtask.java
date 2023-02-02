package tasks;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task{

    protected int epicId;

    public Subtask(String name, String description, int epicId, LocalDateTime startTime, int duration) {
        super(name, description, startTime, duration);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;

    }

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;

    }

    public Subtask(int id, String name, String description, int epicId,LocalDateTime startTime, int duration) {
        super(id, name, description, startTime, duration);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;

    }
    public Subtask(int id, String name, TaskStatus status, String description, int epicId,LocalDateTime startTime, int duration) {
        super(id, name, status, description, startTime, duration);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;

    }

    public int getEpicId(){
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }



    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", startTime=" + startTime + '\'' +
                ", duration=" + duration +
                '}';
    }



    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }


}
