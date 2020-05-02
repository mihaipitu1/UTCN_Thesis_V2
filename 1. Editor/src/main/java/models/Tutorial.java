package models;

public class Tutorial {
    private int id;
    private String title;
    private String description;
    private String task;
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public boolean isEqual(Tutorial obj) {
        if(this.getId() != obj.getId()) {
            return false;
        }
        if(!this.getTitle().equals(obj.getTitle())) {
            return false;
        }
        if(!this.getDescription().equals(obj.getDescription())) {
            return false;
        }
        if(!this.getTask().equals(obj.getTask())) {
            return false;
        }
        if(!this.getAnswer().equals(obj.getAnswer())) {
            return false;
        }
        return true;
    }
}
