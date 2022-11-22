import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected String status;
    protected String description;

    public Task (String name, String description) {
        this.name = name;
        this.status = "NEW";
        this.description = description;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
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
        int hash = 17;
        if (name != null){
            hash = hash + name.hashCode();
        }
        hash = 31;
        if (status != null){
            hash = hash + status.hashCode();
        }
        hash = 11;
        if (description != null){
            hash = hash + description.hashCode();
        } return hash;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}
