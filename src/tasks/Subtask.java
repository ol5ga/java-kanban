package tasks;
import java.util.Objects;

public class Subtask extends Task{

    protected int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(int id, String name, String description, int epicId) {
        super(id, name, description);
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
                '}';
    }



    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }


}
