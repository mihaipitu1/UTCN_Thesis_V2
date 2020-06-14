package models;

import lombok.Data;

@Data
public class Tutorial {
    private int id;
    private String title;
    private String description;
    private String task;
    private String answer;

    @Override
    public String toString() {
        return this.id + ". " + this.title;
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
