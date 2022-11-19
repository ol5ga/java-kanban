import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(int id, String name, String description, String status) {
        super(id, name, status, description);
    }

    public Epic(String name, String description, String status) {
        super(name, status, description);
    }

    @Override
    public boolean isEpic(){
        return true;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void addSubtaskId(int Id){
        subtaskId.add(id);
    }

    public ArrayList<Integer> getSubtaskId(){
        return subtaskId;
    }

    public void cleanSubtaskId(){
        subtaskId.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                "subtaskId=" + subtaskId +
                '}';
    }
}
