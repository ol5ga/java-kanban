public class Subtask extends Task{

    protected int epicId;

    public Subtask(int id, String name, String description, String status, int epicId) {
        super(id, name, status, description);
    }

    public Subtask(String name, String description, String status, int epicId) {
        super(name, status, description);
    }

    public int getEpicId(){
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
