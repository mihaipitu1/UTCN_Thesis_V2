package models;

import lombok.Data;

@Data
public class Guide {
    private int id;
    private String title;
    private String description;
    private String example;

    public boolean isEqual(Guide obj) {
        if(this.getId() != obj.getId()) {
            return false;
        }
        if(!this.getTitle().equals(obj.getTitle())) {
            return false;
        }
        if(!this.getDescription().equals(obj.getDescription())) {
            return false;
        }
        if(!this.getExample().equals(obj.getExample())) {
            return false;
        }
        return true;
    }
}
